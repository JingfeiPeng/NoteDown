package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.awt.TextArea
import java.awt.TextField

@Composable
fun DocumentEditingArea(textState: MutableState<TextFieldValue>) {
    Column {
        TextField(
            value = textState.value,
            label = { Text("Jot something down...") },
            onValueChange = { textState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}