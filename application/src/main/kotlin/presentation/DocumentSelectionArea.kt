package presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import java.io.File

fun saveText(text: String) {
    File("src/save.txt").writeText(text)
}

fun loadText(textState: MutableState<TextFieldValue>) {
    textState.value = TextFieldValue(File("src/save.txt").readText())
}

@Composable
fun DocumentSelectionArea() {
    var text by remember { mutableStateOf("Select Document Area") }
        Button(onClick = {
            text = "Select Document Area here"
        }) {
            Text(text)
        }
}
