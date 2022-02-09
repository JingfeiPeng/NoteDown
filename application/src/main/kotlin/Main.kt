// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.Modifier

import presentation.*



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
        Box(modifier = Modifier.fillMaxWidth(0.3f)) {
            DocumentSelectionArea()
        }
        Box(modifier = Modifier.fillMaxWidth(0.6f)) {
            DocumentEditingArea()
        }
        MarkdownRendererArea()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
