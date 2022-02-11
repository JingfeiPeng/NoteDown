package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import data.NoteFile
import data.NoteFolder

@Composable
fun DocumentSelectionArea(folders: List<NoteFolder>) {
    val selectedFolder = remember { mutableStateOf<NoteFolder?>(null) }
    val selectedFile = remember { mutableStateOf<NoteFile?>(null) }

    val selectedColour = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
    val unselectedColour = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)

    Row(Modifier.fillMaxSize()) {
        Box {
            Column {
                folders.forEach {
                    val colour = if (selectedFolder.value == it) selectedColour else unselectedColour
                    Button(onClick = {
                        selectedFolder.value = it
                    }, colors = colour) {
                        Text(it.name)
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.6f)) {
            selectedFolder.value?.let {
                Column {
                    it.children.forEach {
                        val colour = if (selectedFile.value == it) selectedColour else unselectedColour
                        Button(onClick = {
                            selectedFile.value = it
                        }, colors = colour) {
                            Text(it.name)
                        }
                    }
                }
            }
        }
    }
}
