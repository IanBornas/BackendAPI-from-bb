# Android Retrofit Integration Guide (From Current Backend)

This README gives ready-to-copy Kotlin files for your Android app.
It matches the backend currently running in this repo.

## 1) Gradle dependencies

Add to your app module `build.gradle.kts`:

```kotlin
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
```

## 2) Android manifest

In `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />

<application
    android:usesCleartextTraffic="true"
    ...>
</application>
```

## 3) Base URLs

- Emulator host: `10.0.2.2`
- Real device host: your PC LAN IP (example `192.168.1.10`)

Backend ports:
- User service: `8080`
- Conversation service: `8081`
- Message service: `8082`

## 4) DTO files

Create these files under `data/model/`.

### `data/model/UserDto.kt`

```kotlin
package com.yourapp.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("username") val username: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("email") val email: String,
    @SerializedName("avatarUrl") val avatarUrl: String?,
    @SerializedName("online") val online: Boolean,
    @SerializedName("lastSeen") val lastSeen: String?,
    @SerializedName("statusMessage") val statusMessage: String?,
    @SerializedName("deviceToken") val deviceToken: String?,
    @SerializedName("lastUpdated") val lastUpdated: String?,
    @SerializedName("created") val created: String?
)
```

### `data/model/ConvConversationDto.kt`

```kotlin
package com.yourapp.data.model

import com.google.gson.annotations.SerializedName

// Conversation model from conv service (port 8081)
data class ConvConversationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("conversationId") val conversationId: Int,
    @SerializedName("conversationName") val conversationName: String,
    @SerializedName("conversationType") val conversationType: String,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("creatorId") val creatorId: Int,
    @SerializedName("lastUpdated") val lastUpdated: String?,
    @SerializedName("created") val created: String?
)
```

### `data/model/CreatorDto.kt`

```kotlin
package com.yourapp.data.model

import com.google.gson.annotations.SerializedName

data class CreatorDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("lastUpdated") val lastUpdated: String?,
    @SerializedName("created") val created: String?
)
```

### `data/model/MessageDto.kt`

```kotlin
package com.yourapp.data.model

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("id") val id: Int,
    @SerializedName("messageId") val messageId: Int,
    @SerializedName("conversationId") val conversationId: Int,
    @SerializedName("senderId") val senderId: Int,
    @SerializedName("content") val content: String,
    @SerializedName("messageType") val messageType: String,
    @SerializedName("sentAt") val sentAt: String?,
    @SerializedName("deliveredAt") val deliveredAt: String?,
    @SerializedName("readAt") val readAt: String?,
    @SerializedName("mediaUrl") val mediaUrl: String?,
    @SerializedName("replyToMessageId") val replyToMessageId: Int,
    @SerializedName("lastUpdated") val lastUpdated: String?,
    @SerializedName("created") val created: String?
)
```

### `data/model/MessConversationDto.kt`

```kotlin
package com.yourapp.data.model

import com.google.gson.annotations.SerializedName

// Conversation model from message service (port 8082)
data class MessConversationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("lastUpdated") val lastUpdated: String?,
    @SerializedName("created") val created: String?
)
```

### `data/model/SenderDto.kt`

```kotlin
package com.yourapp.data.model

import com.google.gson.annotations.SerializedName

data class SenderDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("lastUpdated") val lastUpdated: String?,
    @SerializedName("created") val created: String?
)
```

### `data/model/ReplyToMessageDto.kt`

```kotlin
package com.yourapp.data.model

import com.google.gson.annotations.SerializedName

data class ReplyToMessageDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("lastUpdated") val lastUpdated: String?,
    @SerializedName("created") val created: String?
)
```

## 5) Retrofit API interfaces

Create these files under `data/remote/`.

### `data/remote/UserApi.kt`

```kotlin
package com.yourapp.data.remote

import com.yourapp.data.model.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("api/user")
    suspend fun getUsers(): List<UserDto>

    @GET("api/user/{id}")
    suspend fun getUser(@Path("id") id: Int): UserDto

    @PUT("api/user")
    suspend fun createUser(@Body body: UserDto): UserDto

    @POST("api/user")
    suspend fun updateUser(@Body body: UserDto): UserDto

    @DELETE("api/user/{id}")
    suspend fun deleteUser(@Path("id") id: Int)
}
```

### `data/remote/ConversationApi.kt`

```kotlin
package com.yourapp.data.remote

import com.yourapp.data.model.ConvConversationDto
import com.yourapp.data.model.CreatorDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ConversationApi {
    @GET("api/conversation")
    suspend fun getConversations(): List<ConvConversationDto>

    @GET("api/conversation/{id}")
    suspend fun getConversation(@Path("id") id: Int): ConvConversationDto

    @PUT("api/conversation")
    suspend fun createConversation(@Body body: ConvConversationDto): ConvConversationDto

    @POST("api/conversation")
    suspend fun updateConversation(@Body body: ConvConversationDto): ConvConversationDto

    @DELETE("api/conversation/{id}")
    suspend fun deleteConversation(@Path("id") id: Int)

    @GET("api/creator")
    suspend fun getCreators(): List<CreatorDto>

    @GET("api/creator/{id}")
    suspend fun getCreator(@Path("id") id: Int): CreatorDto

    @PUT("api/creator")
    suspend fun createCreator(@Body body: CreatorDto): CreatorDto

    @POST("api/creator")
    suspend fun updateCreator(@Body body: CreatorDto): CreatorDto

    @DELETE("api/creator/{id}")
    suspend fun deleteCreator(@Path("id") id: Int)
}
```

### `data/remote/MessageApi.kt`

```kotlin
package com.yourapp.data.remote

import com.yourapp.data.model.MessageDto
import com.yourapp.data.model.MessConversationDto
import com.yourapp.data.model.ReplyToMessageDto
import com.yourapp.data.model.SenderDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MessageApi {
    @GET("api/message")
    suspend fun getMessages(): List<MessageDto>

    @GET("api/message/{id}")
    suspend fun getMessage(@Path("id") id: Int): MessageDto

    @PUT("api/message")
    suspend fun createMessage(@Body body: MessageDto): MessageDto

    @POST("api/message")
    suspend fun updateMessage(@Body body: MessageDto): MessageDto

    @DELETE("api/message/{id}")
    suspend fun deleteMessage(@Path("id") id: Int)

    @GET("api/conversation")
    suspend fun getConversations(): List<MessConversationDto>

    @GET("api/conversation/{id}")
    suspend fun getConversation(@Path("id") id: Int): MessConversationDto

    @PUT("api/conversation")
    suspend fun createConversation(@Body body: MessConversationDto): MessConversationDto

    @POST("api/conversation")
    suspend fun updateConversation(@Body body: MessConversationDto): MessConversationDto

    @DELETE("api/conversation/{id}")
    suspend fun deleteConversation(@Path("id") id: Int)

    @GET("api/sender")
    suspend fun getSenders(): List<SenderDto>

    @GET("api/sender/{id}")
    suspend fun getSender(@Path("id") id: Int): SenderDto

    @PUT("api/sender")
    suspend fun createSender(@Body body: SenderDto): SenderDto

    @POST("api/sender")
    suspend fun updateSender(@Body body: SenderDto): SenderDto

    @DELETE("api/sender/{id}")
    suspend fun deleteSender(@Path("id") id: Int)

    @GET("api/replyToMessage")
    suspend fun getReplyToMessages(): List<ReplyToMessageDto>

    @GET("api/replyToMessage/{id}")
    suspend fun getReplyToMessage(@Path("id") id: Int): ReplyToMessageDto

    @PUT("api/replyToMessage")
    suspend fun createReplyToMessage(@Body body: ReplyToMessageDto): ReplyToMessageDto

    @POST("api/replyToMessage")
    suspend fun updateReplyToMessage(@Body body: ReplyToMessageDto): ReplyToMessageDto

    @DELETE("api/replyToMessage/{id}")
    suspend fun deleteReplyToMessage(@Path("id") id: Int)
}
```

## 6) Retrofit client setup

Create `data/remote/RetrofitClients.kt`:

```kotlin
package com.yourapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClients {
    // Emulator: 10.0.2.2, real device: your machine LAN IP
    private const val HOST = "10.0.2.2"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userApi: UserApi = retrofit("http://$HOST:8080/")
        .create(UserApi::class.java)

    val conversationApi: ConversationApi = retrofit("http://$HOST:8081/")
        .create(ConversationApi::class.java)

    val messageApi: MessageApi = retrofit("http://$HOST:8082/")
        .create(MessageApi::class.java)
}
```

## 7) Example usage in ViewModel

```kotlin
viewModelScope.launch {
    runCatching {
        RetrofitClients.userApi.getUsers()
    }.onSuccess { users ->
        // update state
    }.onFailure { err ->
        // show error
    }
}
```

## 8) Copilot prompt you can paste in Android Studio

```text
Create Kotlin files for my Android app using this backend contract:
- User API at http://10.0.2.2:8080/api/user
- Conversation API at http://10.0.2.2:8081/api/conversation and /api/creator
- Message API at http://10.0.2.2:8082/api/message, /api/conversation, /api/sender, /api/replyToMessage

Generate these files:
- data/model/UserDto.kt
- data/model/ConvConversationDto.kt
- data/model/CreatorDto.kt
- data/model/MessageDto.kt
- data/model/MessConversationDto.kt
- data/model/SenderDto.kt
- data/model/ReplyToMessageDto.kt
- data/remote/UserApi.kt
- data/remote/ConversationApi.kt
- data/remote/MessageApi.kt
- data/remote/RetrofitClients.kt

Use Retrofit 2 + Gson + suspend functions.
Use String? for backend Date fields.
```

## 9) Important notes

- Backend uses `PUT` for create and `POST` for update.
- Dates are Java `Date` on backend; client currently treats them as `String?` for safety.
- There are two conversation models; keep both DTOs separate.
