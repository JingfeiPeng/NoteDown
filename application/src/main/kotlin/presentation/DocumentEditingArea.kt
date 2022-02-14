package presentation

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import java.awt.TextArea
import java.awt.TextField

@Composable
fun DocumentEditingArea(textState: MutableState<TextFieldValue>) {
    TextField(
        value = textState.value,
        label = { Text("Jot something down...") },
        onValueChange = { textState.value = it },
    )
}