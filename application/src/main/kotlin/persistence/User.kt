package persistence

import java.io.File
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

class User {
    var userId: String = ""
    private val savedReferenceFolder = System.getProperty("user.home") + "/NotesTaker"
    private val savedReference = ".user"
    val STRING_LENGTH: Int = 8
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun getCurrentUserId(): String {
        if (userId == "") {
            if (this.isUserStored()) {
                this.userId = loadUserId()
            } else {
                this.userId = randomAlphanumeric()
                storedUserId(this.userId)
            }
        }
        return this.userId
    }

    private fun randomAlphanumeric(): String {
        return ThreadLocalRandom.current()
            .ints(STRING_LENGTH.toLong(), 0, charPool.size)
            .asSequence()
            .map(charPool::get)
            .joinToString("")
    }

    private fun isUserStored(): Boolean {
        return File(this.savedReferenceFolder, this.savedReference).exists()
    }

    fun storedUserId(text: String) {
        File(this.savedReferenceFolder, this.savedReference).writeText(text)
    }

    private fun loadUserId(): String {
        return File(this.savedReferenceFolder, this.savedReference).readText(Charsets.UTF_8)
    }

}