package persistence

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class UserTest {
    private val savedReferenceFolder = System.getProperty("user.home") + "/NotesTaker"
    private val savedReference = ".user"
    private val userid = File(savedReferenceFolder, savedReference)

    @Test
    fun checkForUser() {
        val id: String = User().getCurrentUserId();
        assert(id.length == User().STRING_LENGTH)
    }

    @Test
    fun validateSavedUser() {
        val id: String = User().getCurrentUserId();
        assert(id == userid.readText())
    }
}

