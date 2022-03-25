package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    foldersState: MutableState<ArrayList<NoteFolder>>,
) {
    val folders = foldersState.value

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
                                } ?: run {
                                    selectedFile.value = null
                                    textState.value = TextFieldValue()
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
                                val newFolder = FileIO.makeFolder(it)
                                textState.value = TextFieldValue()
                                selectedFile.value = null
                                newFolderText = null
                                foldersState.value = FileIO.readNotesFolder()
                                selectedFolder.value = foldersState.value.find { f -> f.name == newFolder!!.name }
                            }
                            true
                        }.onFocusChanged { focusState ->
                            if (!focusState.isFocused && it.isNotEmpty()) {
                                val newFolder = FileIO.makeFolder(it)
                                textState.value = TextFieldValue()
                                selectedFile.value = null
                                newFolderText = null
                                foldersState.value = FileIO.readNotesFolder()
                                selectedFolder.value = foldersState.value.find { f -> f.name == newFolder!!.name }
                            }
                        },
                        value = it,
                        singleLine = true,
                        onValueChange = {
                            newFolderText = it
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1.0f))
                Row(modifier = Modifier.padding(1.dp)) {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(0.5f).padding(0.5.dp),
                        onClick = {
                            newFolderText = ""
                            focusFolder = true
                        }) {
                        Text("+")
                    }
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(1f).padding(0.5.dp),
                        enabled = selectedFolder.value != null,
                        onClick = {
                            selectedFolder.value?.run(FileIO::deleteFolder)
                            if (folders.size == 1) {
                                textState.value = TextFieldValue()
                                selectedFile.value = null
                                selectedFolder.value = null
                                foldersState.value = FileIO.readNotesFolder()
                            } else {
                                val idx = folders.indexOf(selectedFolder.value)
                                val newIdx = if (idx == folders.size - 1) idx - 1 else idx
                                textState.value = TextFieldValue()
                                selectedFile.value = null
                                foldersState.value = FileIO.readNotesFolder()
                                selectedFolder.value = foldersState.value[newIdx]
                            }
                        }) {
                        Text("-")
                    }
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
                                    foldersState.value = FileIO.readNotesFolder()
                                    selectedFolder.value = foldersState.value.find { it.name == selectedFolder.value!!.name }
                                    selectedFile.value = selectedFolder.value!!.children.find { it.name == selectedFile.value!!.name }

                                }
                                true
                            }.onFocusChanged { focusState ->
                                if (!focusState.isFocused && it.isNotEmpty()) {
                                    val dir = selectedFolder.value
                                    if (dir != null) {
                                        createFile(selectedFolder, selectedFile, textState, dir, it)
                                        newFileText = null
                                        foldersState.value = FileIO.readNotesFolder()
                                        selectedFolder.value = foldersState.value.find { it.name == selectedFolder.value!!.name }
                                        selectedFile.value = selectedFolder.value!!.children.find { it.name == selectedFile.value!!.name }
                                    }
                                }
                            },
                            value = it,
                            singleLine = true,
                            onValueChange = {
                                newFileText = it
                            }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1.0f))


                    Row(modifier = Modifier.padding(1.dp)) {
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(0.5f).padding(0.5.dp),

                            onClick = {
                                newFileText = ""
                                focusFile = true
                            }) {
                            Text("+")
                        }
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(1f).padding(0.5.dp),
                            enabled = selectedFile.value != null,
                            onClick = {
                                selectedFile.value?.run(FileIO::deleteFile)
                                if (it.children.size == 1) {
                                    textState.value = TextFieldValue()
                                    selectedFile.value = null
                                } else {
                                    val idx = it.children.indexOf(selectedFile.value)
                                    val newIdx = if (idx == it.children.size - 1) idx - 1 else idx + 1
                                    val newFile = it.children[newIdx]
                                    updateSelectedFile(selectedFile, textState, newFile)
                                }
                                foldersState.value = FileIO.readNotesFolder()
                                selectedFolder.value = foldersState.value.find { folder -> folder.name == it.name }
                                selectedFile.value = selectedFolder.value!!.children.find { it.name == selectedFile.value!!.name }
                            }) {
                            Text("-")
                        }
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
