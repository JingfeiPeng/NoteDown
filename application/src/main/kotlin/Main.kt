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
import data.NoteFile
import data.NoteFolder
import presentation.DocumentEditingArea
import presentation.DocumentSelectionArea
import presentation.MarkdownRendererArea
import presentation.TextCustomizationMenu


@Composable
@Preview
fun App() {
    MaterialTheme {
        BoxWithConstraints {
            Column {
                TextCustomizationMenu()
                MainArea()
            }
        }
    }
}

@Composable
fun MainArea() {
    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight()) {
            DocumentSelectionArea(
                listOf(
                    NoteFolder(
                        "dir1",
                        listOf(
                            NoteFile("file0"),
                            NoteFile("file1")
                        )
                    ), NoteFolder(
                        "dir2",
                        listOf(
                            NoteFile("file2"),
                            NoteFile("file3")
                        )
                    )
                )
            )
        }
        Box(modifier = Modifier.fillMaxWidth(0.6f)) {
            DocumentEditingArea()
        }
        MarkdownRendererArea()
    }
}

@Composable
fun FrameWindowScope.MenuItems() {
    MenuBar {
        Menu("File") {}
        Menu("Notes Calendar View") {}
        Menu("Help") {}
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MenuItems()
        App()
    }
}
