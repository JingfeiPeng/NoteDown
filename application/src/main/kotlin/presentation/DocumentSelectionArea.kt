package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
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

fun createFile(
    selectedFolder: MutableState<NoteFolder?>,
    selectedFile: MutableState<NoteFile?>,
    textState: MutableState<TextFieldValue>,
    dir: NoteFolder,
    name: String,
) {
    val (folder, file) = FileIO.makeFile(dir, name)
    if (folder != null) {
        selectedFolder.value = folder
    }
    if (file != null) {
        updateSelectedFile(selectedFile, textState, file)
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DocumentSelectionArea(
    selectedFolder: MutableState<NoteFolder?>,
    selectedFile: MutableState<NoteFile?>,
    textState: MutableState<TextFieldValue>,
) {
    val folders = FileIO.readNotesFolder();

    val newFolderFocusRequester = remember { FocusRequester() }
    val newFileFocusRequester = remember { FocusRequester() }

    var focusFolder by remember { mutableStateOf(false) }
    var focusFile by remember { mutableStateOf(false) }

    var newFolderText by remember { mutableStateOf<String?>(null) }
    var newFileText by remember { mutableStateOf<String?>(null) }

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

                newFolderText?.let {
                    TextField(
                        modifier = Modifier.focusRequester(newFolderFocusRequester).onKeyEvent { keyEvent ->
                            if (keyEvent.key.keyCode == Key.Enter.keyCode) {
                                selectedFolder.value = FileIO.makeFolder(it)
                                newFolderText = null
                            }
                            true
                        },
                        value = it,
                        singleLine = true,
                        onValueChange = {
                            newFolderText = it
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1.0f))

                Button(modifier = Modifier.fillMaxWidth().padding(7.dp),
                    onClick = {
                        newFolderText = ""
                        focusFolder = true
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

                    newFileText?.let {
                        TextField(
                            modifier = Modifier.focusRequester(newFileFocusRequester).onKeyEvent { keyEvent ->
                                val dir = selectedFolder.value
                                if (keyEvent.key.keyCode == Key.Enter.keyCode && dir != null) {
                                    createFile(selectedFolder, selectedFile, textState, dir, it)
                                    newFileText = null
                                }
                                true
                            },
                            value = it,
                            singleLine = true,
                            onValueChange = {
                                newFileText = it
                            }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1.0f))

                    Button(modifier = Modifier.fillMaxWidth().padding(8.dp),
                        onClick = {
                            newFileText = ""
                            focusFile = true
                        }) {
                        Text("+ File")
                    }
                }
            }
        }
    }

    DisposableEffect(focusFolder) {
        if (focusFolder) {
            newFolderFocusRequester.requestFocus()
        }
        onDispose { }
    }

    DisposableEffect(focusFile) {
        if (focusFile) {
            newFileFocusRequester.requestFocus()
        }
        onDispose { }
    }
}
