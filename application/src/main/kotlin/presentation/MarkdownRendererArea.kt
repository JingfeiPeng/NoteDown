package presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun MarkdownRendererArea(textState: MutableState<TextFieldValue>) {
    Text(textState.value.text)
}
