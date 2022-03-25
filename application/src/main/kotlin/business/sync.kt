package business

import database.Document
import database.UserFile
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import persistence.User
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

@Serializable
data class UserFiles (
    val files: List<UserFile>,
    val userId: String,
)


object Sync {

    val client = HttpClient.newBuilder().build();

    fun getUserFiles(): List<UserFile> {
        val userID = User().getCurrentUserId()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/userFiles?userId=$userID"))
            .build()
        val response = client.sendAsync(request, BodyHandlers.ofString())
        return Json.decodeFromString<List<UserFile>>(response.get().body())
    }

    fun postUserFiles(files: List<UserFile>) {
        val userID = User().getCurrentUserId()

        val requestBody = Json.encodeToString(UserFiles(
            files, userID,
        ))
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/userFiles"))
            .header("Content-Type","application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()
        val response = client.sendAsync(request, BodyHandlers.ofString())
    }
}