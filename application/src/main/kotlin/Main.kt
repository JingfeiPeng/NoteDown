// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import presentation.DocumentEditingArea
import presentation.DocumentSelectionArea
import presentation.MarkdownRendererArea
import presentation.TextCustomizationMenu
import androidx.compose.runtime.mutableStateOf


import persistence.FileIO

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import data.NoteFile
import data.NoteFolder
import presentation.markdown.MarkdownRenderers

@Composable
@Preview
fun App(
    textState: MutableState<TextFieldValue>,
    selectedFolder: MutableState<NoteFolder?>,
    selectedFile: MutableState<NoteFile?>,
) {
    MaterialTheme {
        BoxWithConstraints {
            Column {
                TextCustomizationMenu(textState)
                MainArea(textState, selectedFolder, selectedFile)
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
        MarkdownRendererArea(textState, rendererFun = MarkdownRenderers.MIKE_PENZ_MARKDOWN.renderFun)
    }
}

@Composable
fun FrameWindowScope.MenuItems(
    textState: MutableState<TextFieldValue>,
    file: NoteFile?,
)  {
    MenuBar {
        Menu("File") {
            Item("Save", onClick = {
                if (file != null) {
                    FileIO.saveText(textState.value.text.toString(), file)
                }
            })
        }
        Menu("Notes Calendar View") {}
        Menu("Help") {}
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        // To-do: shouldn't pass the props around and down the children.
        // figure out a way to use redux like store
        val textState = remember { mutableStateOf(TextFieldValue()) }
        val selectedFolder = remember { mutableStateOf<NoteFolder?>(null) }
        val selectedFile = remember { mutableStateOf<NoteFile?>(null) }
        MenuItems(textState, selectedFile.value)
        App(textState, selectedFolder, selectedFile)
    }
}
