package database

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class Document(
    val path: String,
    val createdOn: Long
)