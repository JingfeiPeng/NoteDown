package presentation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun DocumentSelectionArea() {
    var text by remember { mutableStateOf("Select Document Area") }

    Button(onClick = {
        text = "Select Document Area here"
    }) {
        Text(text)
    }
}
