package database

import kotlinx.serialization.*
import util.dateSerializer
import java.util.*

@Serializable
data class Document(
    val path: String,
    @Serializable(with = dateSerializer::class)
    val createdOn: Date,
) {
}
