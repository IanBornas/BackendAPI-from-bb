# BackendAPI Script Run Notes

This guide is for running the automation script in this repo:

`run.ps1`

## 1. Open the correct folder

Open PowerShell in:

`C:\Users\sekir\OneDrive\Desktop\New folder (21)\BackendAPI-from-bb`

Check you are in the right folder:

```powershell
Get-Location
```

## 2. First-time PowerShell setup (if script is blocked)

Run once:

```powershell
Set-ExecutionPolicy -Scope CurrentUser RemoteSigned
```

## 3. Main commands

Build and run everything with Docker (recommended):

```powershell
.\run.ps1
```

Build only:

```powershell
.\run.ps1 -Mode build
```

Run tests for all modules:

```powershell
.\run.ps1 -Mode test
```

Run locally without Docker (requires local MySQL):

```powershell
.\run.ps1 -Mode local
```

Stop Docker containers:

```powershell
.\run.ps1 -Mode down
```

If containers are in a bad state, reset then rerun:

```powershell
docker compose down
.\run.ps1
```

## 4. Services and URLs

When Docker mode is up, services are available at:

- User service: `http://localhost:8080`
- Conversation service: `http://localhost:8081`
- Message service: `http://localhost:8082`

## 5. Required tools

Install and ensure these are available in PATH:

- Java JDK (17+ recommended)
- Maven
- Docker Desktop

Quick checks:

```powershell
java -version
mvn --version
docker --version
```

## 6. Common issues and quick fixes

`JAVA_HOME environment variable is not defined correctly`

Use a JDK path (example):

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

`ports are not available` (port conflict)

Find what uses a port (example 8080):

```powershell
netstat -ano | findstr :8080
```

Then stop the conflicting process or use a different port mapping.

`docker compose up failed`

Use logs:

```powershell
docker compose logs -f
docker compose logs -f sbuserms
docker compose logs -f sbconvms
docker compose logs -f sbmessms
```

## 7. Notes for this repo

These backend modules run together in Docker:

- `user/sbuserms`
- `conv/sbconvms`
- `mess/sbmessms`
- `mysql` (container)

Supporting modules are built as dependencies:

- `user/userdata`
- `conv/convdata`
- `mess/messdata`
