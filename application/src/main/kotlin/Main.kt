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


import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import androidx.compose.material.TextField
import androidx.compose.ui.text.input.TextFieldValue
import java.io.File


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
fun TextFieldDemo() {
    Column(Modifier.padding(16.dp)) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it }
        )
        Text("The textfield has this text: " + textState.value.text)
        MaterialTheme {
            Button(onClick = {
                File("src/save.txt").writeText(textState.value.text.toString())
            }) {
                Text("Save")
            }
        }
fun MainArea() {
    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(0.3f)) {
            DocumentSelectionArea()
        }
        Box(modifier = Modifier.fillMaxWidth(0.6f)) {
            DocumentEditingArea()
        }
        MarkdownRendererArea()
    }
}

@Composable
fun FrameWindowScope.MenuItems()  {
    MenuBar {
        Menu("File") {}
        Menu("Notes Calendar View") {}
        Menu("Help") {}
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        TextFieldDemo()
//        App()
        MenuItems()
        App()
    }
}
