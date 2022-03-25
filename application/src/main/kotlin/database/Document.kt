package database

import kotlinx.serialization.*
import util.DateSerializer
import java.util.*

@Serializable
data class Document(
    val path: String,
    @Serializable(with = DateSerializer::class)
    val createdOn: Date,
) 

@Serializable
data class UserFile (
    val id: Int = 0,
    val userId: String,
    val fileName: String,
    val folderName: String,
    val createdOn: Long,
    val text: String,
)