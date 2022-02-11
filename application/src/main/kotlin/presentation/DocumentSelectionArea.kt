package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import data.NoteFile
import data.NoteFolder

@Composable
fun DocumentSelectionArea(folders: List<NoteFolder>) {
    val selectedFolder = remember { mutableStateOf<NoteFolder?>(null) }
    val selectedFile = remember { mutableStateOf<NoteFile?>(null) }

    Row(Modifier.fillMaxSize()) {
        Box {
            Column {
                folders.forEach {
                    Button(onClick = {
                        selectedFolder.value = it
                    }) {
                        Text(it.name)
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth(0.6f)) {
            selectedFolder.value?.let {
                Column {
                    it.children.forEach {
                        Button(onClick = {
                            selectedFile.value = it
                        }) {
                            Text(it.name)
                        }
                    }
                }
            }
        }
    }
}
