package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import data.NoteFile
import persistence.FileIO
import java.awt.TextArea
import java.awt.TextField

@Composable
fun DocumentEditingArea(
    textState: MutableState<TextFieldValue>,
    selectedFile: MutableState<NoteFile?>,
    ) {
    Column {
        TextField(
            value = textState.value,
            label = { Text("Jot something down...") },
            onValueChange = {
                textState.value = it
                FileIO.saveText(textState.value.text.toString(), selectedFile.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}