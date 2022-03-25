// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.


import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.mutableStateOf



import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.*
import data.NoteFile
import data.NoteFolder
import persistence.FileIO
import persistence.LocalWindowState
import androidx.compose.ui.unit.dp
import business.Sync
import database.UserFile
import presentation.*
import presentation.markdown.MarkdownRenderers

@Composable
@Preview
fun App(
    textState: MutableState<TextFieldValue>,
    selectedFolder: MutableState<NoteFolder?>,
    selectedFile: MutableState<NoteFile?>,
    calendarView: MutableState<Boolean>,
    userSettings: MutableState<Boolean>,
    folders: MutableState<ArrayList<NoteFolder>>,
) {
    MaterialTheme(colors = MaterialTheme.colors.copy(primary = Color(90, 180, 90))) {
        if (calendarView.value) {
            Column {
                CalendarView(calendarView, selectedFolder, selectedFile, textState)
            }
        } else if (userSettings.value) {
            Column {
                UserSettingsView(userSettings)
            }
        } else {
            BoxWithConstraints {
                Column {
                    TopBar(textState)
                    MainArea(textState, selectedFolder, selectedFile, folders)
                }
            }
        }
    }
}

@Composable
fun MainArea(
    textState: MutableState<TextFieldValue>,
    selectedFolder: MutableState<NoteFolder?>,
    selectedFile: MutableState<NoteFile?>,
    folders: MutableState<ArrayList<NoteFolder>>,
) {
    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight()) {
            DocumentSelectionArea(
                selectedFolder,
                selectedFile,
                textState,
                folders,
            )
        }
        Box(modifier = Modifier.fillMaxWidth(0.6f)) {
            DocumentEditingArea(textState, selectedFile)
        }
        MarkdownRendererArea(textState, rendererFun = MarkdownRenderers.SWING_BROWSER_MARKDOWN.renderFun)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FrameWindowScope.MenuItems(
    textState: MutableState<TextFieldValue>,
    file: NoteFile?,
    calendarView: MutableState<Boolean>,
    userSettings: MutableState<Boolean>,
    folders: MutableState<ArrayList<NoteFolder>>,
    ) {
    val alertUser = remember { mutableStateOf<Boolean>(false) };

    if (alertUser.value) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth(0.3f).padding(10.dp),
            onDismissRequest = {
                alertUser.value = false
            },
            title = {
                Text(text = "This overrides all of your local notes")
            },
            text = {
                Text("Do you want to continue")
            },
            buttons = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        alertUser.value = false
                    }
                ) {
                    Text("No")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        alertUser.value = false
                        FileIO.emptyAndRepopulateFiles(Sync.getUserFiles())
                        folders.value = FileIO.readNotesFolder()
                    }
                ) {
                    Text("Yes")
                }
            }
        )
    }

    MenuBar {
        Menu("File") {
            Item("Save", onClick = {
                if (file != null) {
                    FileIO.saveText(textState.value.text.toString(), file)
                }
            })
        }
        Menu("Notes Calendar View") {
            Item("View", onClick = {
                userSettings.value = false
                calendarView.value = !(calendarView.value)
            })
        }
        Menu("User Settings") {
            Item("User ID", onClick = {
                calendarView.value = false
                userSettings.value = !(userSettings.value)
            })
        }
        Menu("Help") {
            Item("Bold CTRL/CMD + B") {}
            Item("Italics CTRL/CMD + I") {}
            Item("Underline CTRL/CMD + U") {}
            Item("Strikethrough CTRL/CMD + S") {}
            Item("Code block CTRL/CMD + W") {}
            Menu("sync") {
                Item("Upload files to cloud", onClick = {
                    Sync.postUserFiles(FileIO.getAllUserFiles())
                })
                Item("Download files to local", onClick = {
                    alertUser.value = true
                })
            }
        }
    }
}


fun main() = application {
    val currentWindow = LocalWindowState()
    Window(
        onCloseRequest = {
            currentWindow.saveState()
            exitApplication()
        },
        title = "NoteDown",
        state = currentWindow.state,
        icon = painterResource("icon.png")
    ) {
        // To-do: shouldn't pass the props around and down the children.
        // figure out a way to use redux like store
        val userSettings = remember { mutableStateOf<Boolean>(false) };
        val calendarView = remember { mutableStateOf<Boolean>(false) };
        val textState = remember { mutableStateOf(TextFieldValue()) }
        val selectedFolder = remember { mutableStateOf<NoteFolder?>(null) }
        val selectedFile = remember { mutableStateOf<NoteFile?>(null) }
        val folders = remember { mutableStateOf<ArrayList<NoteFolder>>(FileIO.readNotesFolder()) }
        MenuItems(textState, selectedFile.value, calendarView, userSettings, folders)
        App(textState, selectedFolder, selectedFile, calendarView, userSettings, folders)
    }
}

