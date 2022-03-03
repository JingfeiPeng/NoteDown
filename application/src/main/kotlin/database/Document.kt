package database

import kotlinx.serialization.*
import java.util.*

fun fromTimestamp(value: Long): Date {
    return Date(value)
}

fun dateToTimestamp(date: Date): Long {
    return date.time
}

@Serializable
data class Document(
    val path: String,
    val createdOn: Long,
) {
    constructor(path: String, createdOn: Date): this(
        path, dateToTimestamp(createdOn)
    ) {}

    fun getCreatedTime(): Date {
        return fromTimestamp(this.createdOn)
    }
}
