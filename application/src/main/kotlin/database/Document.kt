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
