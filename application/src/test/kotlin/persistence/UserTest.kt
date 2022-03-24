package persistence

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class UserTest {
    private val userid = File("user.txt")

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

