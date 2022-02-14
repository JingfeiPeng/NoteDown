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

import persistence.saveText
import persistence.loadText


//import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.material.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import java.awt.MenuItem
import java.io.File
import java.lang.System.exit


@Composable
@Preview
fun App(textState: MutableState<TextFieldValue>) {
    MaterialTheme {
        BoxWithConstraints {
            Column {
                TextCustomizationMenu()
                MainArea(textState)
            }
        }
    }
}

@Composable
fun MainArea(textState: MutableState<TextFieldValue>) {
    Row(Modifier.fillMaxSize()) {

        Box(modifier = Modifier.fillMaxWidth(0.3f)) {
            DocumentSelectionArea()
        }
        Box(modifier = Modifier.fillMaxWidth(0.6f)) {
            DocumentEditingArea(textState)
        }
        MarkdownRendererArea(textState)
    }
}

@Composable
fun FrameWindowScope.MenuItems(textState: MutableState<TextFieldValue>)  {
    MenuBar {
        Menu("File") {
            Item("Save", onClick = { saveText(textState.value.text.toString()) })
            Item("Load", onClick = { loadText(textState) })
        }
        Menu("Notes Calendar View") {}
        Menu("Help") {}
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        MenuItems(textState)
        App(textState)
    }
}
