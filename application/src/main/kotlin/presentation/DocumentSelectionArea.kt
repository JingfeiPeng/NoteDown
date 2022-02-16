package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.NoteFile
import data.NoteFolder
import persistence.FileIO

fun updateSelectedFile(
    selectedFile: MutableState<NoteFile?>,
    textState: MutableState<TextFieldValue>,
    file: NoteFile,
) {
    selectedFile.value = file
    FileIO.loadFile(textState, file)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DocumentSelectionArea(
    selectedFolder: MutableState<NoteFolder?>,
    selectedFile: MutableState<NoteFile?>,
    textState: MutableState<TextFieldValue>,
) {
    val folders = FileIO.readNotesFolder();

    // initialize selected folder and foles
    if (folders.size > 0 && selectedFolder.value == null) {
        selectedFolder.value = folders[0]
        if (folders[0].children.size > 0) {
            updateSelectedFile(selectedFile, textState, folders[0].children[0])
        }
    }

    val selectedColour = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    val unselectedColour = ButtonDefaults.buttonColors(backgroundColor = Color.White)

    Row(Modifier.fillMaxSize().padding(top = 10.dp)) {
        Box(modifier = Modifier.fillMaxWidth(0.5f)) {
            Column {
                folders.forEach {
                    val colour = if (selectedFolder.value == it) selectedColour else unselectedColour
                    CompositionLocalProvider(
                        LocalMinimumTouchTargetEnforcement provides false,
                    ) {
                        TextButton(modifier = Modifier.fillMaxWidth(), shape = RectangleShape, onClick = {
                            if (selectedFolder.value != it) {
                                selectedFolder.value = it
                                it.children.firstOrNull()?.let { file ->
                                    updateSelectedFile(selectedFile, textState, file)
                                }
                            }
                        }, colors = colour) {
                            Text(it.name, textAlign = TextAlign.Left)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1.0f))

                Button(modifier = Modifier.fillMaxWidth().padding(8.dp),
                    onClick = {
                        println("New Section")
                    }) {
                    Text("+ Section")
                }
            }
        }

        Divider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Box {
            selectedFolder.value?.let {
                Column {
                    it.children.forEach {
                        val colour = if (selectedFile.value == it) selectedColour else unselectedColour
                        CompositionLocalProvider(
                            LocalMinimumTouchTargetEnforcement provides false,
                        ) {
                            TextButton(
                                modifier = Modifier.fillMaxWidth().height(40.dp),
                                shape = RectangleShape,
                                onClick = {
                                    updateSelectedFile(selectedFile, textState, it)
                                },
                                colors = colour,
                            ) {
                                Text(it.name)
                            }
                        }
                    }


                    Spacer(modifier = Modifier.weight(1.0f))

                    Button(modifier = Modifier.fillMaxWidth().padding(8.dp),
                        onClick = {
                            println("New File")
                        }) {
                        Text("+ File")
                    }
                }
            }
        }
    }
}
