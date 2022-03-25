// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.


import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import data.NoteFile
import data.NoteFolder
import persistence.FileIO
import persistence.LocalWindowState
import presentation.*
import presentation.markdown.MarkdownRenderers
import java.io.File

@Composable
@Preview
fun App(
    textState: MutableState<TextFieldValue>,
    selectedFolder: MutableState<NoteFolder?>,
    selectedFile: MutableState<NoteFile?>,
    calendarView: MutableState<Boolean>,
    userSettings: MutableState<Boolean>,
) {
    if (calendarView.value) {
        MaterialTheme {
            Column {
                CalendarView(calendarView, selectedFolder, selectedFile, textState)
            }
        }
    } else if (userSettings.value) {
        MaterialTheme {
            Column {
                UserSettingsView(userSettings)
            }
        }
    }
    else {
        MaterialTheme {
            BoxWithConstraints {
                Column {
                    TopBar(textState)
                    MainArea(textState, selectedFolder, selectedFile)
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
) {
    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight()) {
            DocumentSelectionArea(
                selectedFolder,
                selectedFile,
                textState,
            )
        }
        Box(modifier = Modifier.fillMaxWidth(0.6f)) {
            DocumentEditingArea(textState, selectedFile)
        }
        MarkdownRendererArea(textState, rendererFun = MarkdownRenderers.SWING_BROWSER_MARKDOWN.renderFun)
    }
}

@Composable
fun FrameWindowScope.MenuItems(
    textState: MutableState<TextFieldValue>,
    file: NoteFile?,
    calendarView: MutableState<Boolean>,
    userSettings: MutableState<Boolean>
)  {
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
        Menu("Help") {}

    }
}


fun main() = application {
    var currentWindow = LocalWindowState()
    Window(onCloseRequest = {
        currentWindow.saveState()
        exitApplication()
    },
        title = "NoteDown",
        state = currentWindow.state,
        icon = painterResource("icon.png")) {
            val userSettings = remember { mutableStateOf<Boolean>(false) };
            val calendarView = remember { mutableStateOf<Boolean>(false) };
            val textState = remember { mutableStateOf(TextFieldValue()) }
            val selectedFolder = remember { mutableStateOf<NoteFolder?>(null) }
            val selectedFile = remember { mutableStateOf<NoteFile?>(null) }
            MenuItems(textState, selectedFile.value, calendarView, userSettings)
            App(textState, selectedFolder, selectedFile, calendarView, userSettings)
    }
}

