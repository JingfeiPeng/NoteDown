package presentation

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import java.io.File

fun saveText(text: String) {
    File("src/save.txt").writeText(text)
}

fun loadText(textState: MutableState<TextFieldValue>) {
    textState.value = TextFieldValue(File("src/save.txt").readText())
}