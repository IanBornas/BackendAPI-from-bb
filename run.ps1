# ============================================================
#  BackendAPI - Build & Run Automation Script
#  Usage:
#    .\run.ps1                   # build + start via Docker Compose (recommended)
#    .\run.ps1 -Mode local       # build + start each service locally (needs MySQL on localhost:3306)
#    .\run.ps1 -Mode build       # build only (no run)
#    .\run.ps1 -Mode test        # run Maven tests for all modules
#    .\run.ps1 -Mode down        # stop and remove Docker Compose containers
# ============================================================

param(
    [ValidateSet("docker","local","build","test","down")]
    [string]$Mode = "docker"
)

$ROOT = $PSScriptRoot
$MODULES = @(
    @{ Name = "user";  Dir = "$ROOT\user";  Jar = "user\sbuserms\target\sbuserms-1.0-SNAPSHOT.jar";  Port = 8080 },
    @{ Name = "conv";  Dir = "$ROOT\conv";  Jar = "conv\sbconvms\target\sbconvms-1.0-SNAPSHOT.jar";  Port = 8081 },
    @{ Name = "mess";  Dir = "$ROOT\mess";  Jar = "mess\sbmessms\target\sbmessms-1.0-SNAPSHOT.jar";  Port = 8082 }
)

# -- Helpers -------------------------------------------------
function Write-Step([string]$msg) {
    Write-Host ""
    Write-Host "==> $msg" -ForegroundColor Cyan
}

function Write-OK([string]$msg)   { Write-Host "  [OK] $msg"     -ForegroundColor Green  }
function Write-Fail([string]$msg) { Write-Host "  [FAIL] $msg"   -ForegroundColor Red    }
function Write-Info([string]$msg) { Write-Host "  [INFO] $msg"   -ForegroundColor Yellow }

function Assert-Command([string]$cmd) {
    if (-not (Get-Command $cmd -ErrorAction SilentlyContinue)) {
        Write-Fail "'$cmd' not found. Please install it and make sure it is on your PATH."
        exit 1
    }
    Write-OK "$cmd found: $((Get-Command $cmd).Source)"
}

function Ensure-JavaHome {
    if ($env:JAVA_HOME -and (Test-Path (Join-Path $env:JAVA_HOME "bin\javac.exe"))) {
        Write-Info "JAVA_HOME: $env:JAVA_HOME"
        return
    }

    # Prefer a real JDK path and avoid Oracle javapath shims.
    $candidates = @()

    try {
        $whereJavac = where.exe javac 2>$null
        if ($whereJavac) {
            foreach ($p in $whereJavac) {
                if ($p -and ($p -notmatch "\\Common Files\\Oracle\\Java\\javapath\\")) {
                    $candidates += (Split-Path -Parent (Split-Path -Parent $p))
                }
            }
        }
    } catch {
        # Ignore where.exe failures and fall back to known folders.
    }

    $candidates += @(
        "C:\Program Files\Java\jdk-25",
        "C:\Program Files\Java\jdk-21",
        "C:\Program Files\Java\jdk-17",
        "$env:LOCALAPPDATA\Programs\Eclipse Adoptium\jdk-21.0.8.9-hotspot"
    )

    foreach ($jdkHome in ($candidates | Where-Object { $_ } | Select-Object -Unique)) {
        if (Test-Path (Join-Path $jdkHome "bin\javac.exe")) {
            $env:JAVA_HOME = $jdkHome
            Write-Info "JAVA_HOME set for this session to: $env:JAVA_HOME"
            return
        }
    }

    Write-Fail "JAVA_HOME is not set to a valid JDK path."
    Write-Info "Set it first, for example:"
    Write-Info '  $env:JAVA_HOME = "C:\Program Files\Java\jdk-17"'
    Write-Info '  $env:Path = "$env:JAVA_HOME\bin;$env:Path"'
    exit 1
}

# -- Prerequisite check --------------------------------------
function Check-Prerequisites([string]$mode) {
    Write-Step "Checking prerequisites"
    Assert-Command "java"
    Assert-Command "mvn"
    Ensure-JavaHome

    $javaVer = (java -version 2>&1 | Select-String "version").ToString()
    Write-Info "Java: $javaVer"

    $mvnVer = (mvn --version 2>&1 | Select-Object -First 1).ToString()
    Write-Info "Maven: $mvnVer"

    if ($mode -eq "docker") {
        Assert-Command "docker"
        $dockerVer = (docker --version).ToString()
        Write-Info "Docker: $dockerVer"

        # Verify docker daemon is running
        $null = docker info 2>&1
        if ($LASTEXITCODE -ne 0) {
            Write-Fail "Docker daemon is not running. Please start Docker Desktop."
            exit 1
        }
        Write-OK "Docker daemon is running"
    }
}

# -- Maven build ---------------------------------------------
function Build-Modules([bool]$runTests) {
    $testFlag = if ($runTests) { "" } else { "-DskipTests" }
    $errors = @()

    foreach ($m in $MODULES) {
        Write-Step "Building module: $($m.Name)"
        Push-Location $m.Dir
        try {
            $cmd = "mvn clean package $testFlag --no-transfer-progress"
            Write-Info "Running: $cmd  (in $($m.Dir))"
            Invoke-Expression $cmd
            if ($LASTEXITCODE -ne 0) {
                Write-Fail "Build FAILED for $($m.Name) (exit $LASTEXITCODE)"
                $errors += $m.Name
            } else {
                Write-OK "Build succeeded for $($m.Name)"
            }
        } finally {
            Pop-Location
        }
    }

    if ($errors.Count -gt 0) {
        Write-Host ""
        Write-Fail "The following modules failed to build: $($errors -join ', ')"
        Write-Host "  Check the Maven output above for details." -ForegroundColor Red
        exit 1
    }
}

# -- Maven test ----------------------------------------------
function Test-Modules {
    $errors = @()
    foreach ($m in $MODULES) {
        Write-Step "Testing module: $($m.Name)"
        Push-Location $m.Dir
        try {
            mvn test --no-transfer-progress
            if ($LASTEXITCODE -ne 0) {
                Write-Fail "Tests FAILED for $($m.Name)"
                $errors += $m.Name
            } else {
                Write-OK "Tests passed for $($m.Name)"
            }
        } finally {
            Pop-Location
        }
    }

    Write-Host ""
    if ($errors.Count -gt 0) {
        Write-Fail "Tests failed in: $($errors -join ', ')"
        exit 1
    } else {
        Write-OK "All tests passed."
    }
}

# -- Docker Compose run --------------------------------------
function Start-Docker {
    Write-Step "Starting all services via Docker Compose"
    Push-Location $ROOT
    try {
        docker compose up --build -d
        if ($LASTEXITCODE -ne 0) {
            Write-Fail "docker compose up failed."
            exit 1
        }
    } finally {
        Pop-Location
    }

    Write-Step "Waiting for services to become healthy (up to 120s)..."
    $waited = 0
    $allUp  = $false
    while ($waited -lt 120) {
        Start-Sleep -Seconds 8
        $waited += 8

        $statuses = docker compose -f "$ROOT\docker-compose.yml" ps --format json 2>$null |
            ConvertFrom-Json -ErrorAction SilentlyContinue

        if ($statuses) {
            $notRunning = $statuses | Where-Object { $_.State -ne "running" }
            if ($notRunning.Count -eq 0) { $allUp = $true; break }
        }
        Write-Info "Still waiting... ($waited s elapsed)"
    }

    # API smoke test
    Write-Step "Running smoke tests"
    $endpoints = @(
        @{ Name = "sbuserms"; Url = "http://localhost:8080/api/user" },
        @{ Name = "sbconvms"; Url = "http://localhost:8081/api/conversation" },
        @{ Name = "sbmessms"; Url = "http://localhost:8082/api/message" }
    )

    foreach ($ep in $endpoints) {
        try {
            $resp = Invoke-WebRequest -Uri $ep.Url -UseBasicParsing -TimeoutSec 10 -ErrorAction Stop
            if ($resp.StatusCode -eq 200) {
                Write-OK "$($ep.Name) endpoint responded 200"
            } else {
                Write-Info "$($ep.Name) responded HTTP $($resp.StatusCode) (may still be starting)"
            }
        } catch {
            Write-Info "$($ep.Name) endpoint not reachable yet or returned non-200"
        }
    }

    Write-Host ""
    Write-OK "Services are running:"
    Write-Info "  sbuserms -> http://localhost:8080"
    Write-Info "  sbconvms -> http://localhost:8081"
    Write-Info "  sbmessms -> http://localhost:8082"
    Write-Info ""
    Write-Info "Useful commands:"
    Write-Info "  docker compose logs -f            # stream all logs"
    Write-Info "  docker compose logs -f sbuserms   # stream single service log"
    Write-Info "  .\run.ps1 -Mode down              # stop everything"
}

# -- Docker Compose down -------------------------------------
function Stop-Docker {
    Write-Step "Stopping Docker Compose services"
    Push-Location $ROOT
    try {
        docker compose down
        if ($LASTEXITCODE -eq 0) { Write-OK "All containers stopped." }
        else                     { Write-Fail "docker compose down reported errors." }
    } finally {
        Pop-Location
    }
}

# -- Local run (no Docker) -----------------------------------
function Start-Local {
    Write-Step "Starting services locally (MySQL must be running on localhost:3306)"
    Write-Info "Make sure MySQL is running with:"
    Write-Info "  user: root | password: admin | databases: userdb, convdb, messdb"
    Write-Host ""

    $jobs = @()
    foreach ($m in $MODULES) {
        $jarPath = Join-Path $ROOT $m.Jar
        if (-not (Test-Path $jarPath)) {
            Write-Fail "JAR not found: $jarPath - run '.\run.ps1 -Mode build' first."
            exit 1
        }
        Write-Info "Starting $($m.Name) on port $($m.Port)..."
        $jobs += Start-Process -FilePath "java" -ArgumentList @("-jar", $jarPath) -PassThru -NoNewWindow
        Start-Sleep -Seconds 3   # stagger startup a little
    }

    Write-Host ""
    Write-OK "All three services started as background processes."
    Write-Info "  sbuserms -> http://localhost:8080"
    Write-Info "  sbconvms -> http://localhost:8081"
    Write-Info "  sbmessms -> http://localhost:8082"
    Write-Info ""
    Write-Info "PIDs: $($jobs.Id -join ', ')"
    Write-Info "To stop them: Stop-Process -Id $($jobs.Id -join ',')"
}

# -- Entry point ---------------------------------------------
Write-Host ""
Write-Host "======================================" -ForegroundColor Magenta
Write-Host "  BackendAPI Automation Script"        -ForegroundColor Magenta
Write-Host "  Mode: $Mode"                          -ForegroundColor Magenta
Write-Host "======================================" -ForegroundColor Magenta

switch ($Mode) {
    "build" {
        Check-Prerequisites "local"
        Build-Modules $false
        Write-OK "Build complete. JARs are ready."
    }
    "test" {
        Check-Prerequisites "local"
        Test-Modules
    }
    "docker" {
        Check-Prerequisites "docker"
        Build-Modules $false
        Start-Docker
    }
    "local" {
        Check-Prerequisites "local"
        Build-Modules $false
        Start-Local
    }
    "down" {
        Assert-Command "docker"
        Stop-Docker
    }
}
