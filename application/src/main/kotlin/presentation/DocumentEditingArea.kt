package presentation

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.TextFieldValue
import business.TextCustomization
import data.NoteFile
import persistence.FileIO


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DocumentEditingArea(
    textState: MutableState<TextFieldValue>,
    selectedFile: MutableState<NoteFile?>,
    ) {
    Column {
        if (selectedFile.value == null) {
            Text("Create a section and note to start editing",
                style = MaterialTheme.typography.body2)
        }
        val requester = remember { FocusRequester() }
        Box(
            Modifier
                .onKeyEvent {
                    if (it.isMetaPressed) {
                        when (it.key) {
                            Key.B -> {
                                TextCustomization.appendAroundSelected(textState, "**")
                                true
                            }
                            Key.U -> {
                                TextCustomization.appendAroundSelected(textState, "*")
                                true
                            }
                            Key.L -> {
                                TextCustomization.appendAroundSelected(textState, "<u>", "<ul>")
                                true
                            }
                            Key.S -> {
                                TextCustomization.appendAroundSelected(textState, "~")
                                true
                            }
                            Key.C -> {
                                TextCustomization.appendAroundSelected(textState, "\n```\n")
                                true
                            }
                        }
                        false
                    }
                    else {
                        // let other handlers receive this event
                        false
                    }
                }
                .focusable()
                .focusRequester(requester)
        ) {

            Column {
                TextField(
                    value = textState.value,
                    enabled = selectedFile.value != null,
                    label = { Text("Jot something down...") },
                    onValueChange = {
                        textState.value = it
                        FileIO.saveText(textState.value.text.toString(), selectedFile.value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        }
        LaunchedEffect(Unit) {
            requester.requestFocus()
        }
    }
}