package presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import business.TextCustomization
import persistence.FileIO
import java.io.File


// button text placement went out of the button
@Composable
fun CustomizationButton(onClick: () -> Unit, content: @Composable() () -> Unit) {
    val buttonColors = ButtonDefaults.buttonColors(contentColor = Color.White)
    val buttonWidth = 40.dp
    Button(
        onClick = onClick,
        colors = buttonColors,
        modifier = Modifier.width(buttonWidth),
    ) {
        content()
    }
}

@Composable
fun codeBlockButton(textState: MutableState<TextFieldValue>) {
    // need a space for margin
    Text(" ")
    var expanded by remember { mutableStateOf(false) }

    val items = listOf("General","JavaScript", "Python", "Java", "C++")
    Box() {
        Text(
            " Insert Code \n Snippet",
            modifier = Modifier.clickable(onClick = {expanded = true}),
            style = MaterialTheme.typography.body2,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    // Send action to change text
                    when (items[index]) {
                        items[0] -> {
                            TextCustomization.appendAroundSelected(textState, "\n```\n")
                        }
                        items[1] -> {
                            TextCustomization.appendAroundSelected(
                                textState, "\n```javascript\n", "\n```\n"
                            )
                        }
                        items[2] -> {
                            TextCustomization.appendAroundSelected(
                                textState, "\n```python\n", "\n```\n"
                            )
                        }
                        items[3] -> {
                            TextCustomization.appendAroundSelected(
                                textState, "\n```java\n", "\n```\n"
                            )
                        }
                        items[4] -> {
                            TextCustomization.appendAroundSelected(
                                textState, "\n```cpp\n", "\n```\n"
                            )
                        }
                    }
                }) {
                    Text(text = s)
                }
            }
        }
    }
}


@Composable
fun TextCustomizationMenu(textState: MutableState<TextFieldValue>) {
    Box(modifier = Modifier.fillMaxWidth(0.20f)) {
        Text(
            text = "Text Customization Menu",
            style = MaterialTheme.typography.body1
        )
    }
    CustomizationButton(onClick = {
        TextCustomization.appendAroundSelected(textState, "**")
    }) { Text("B", fontWeight=FontWeight.Bold) }
    CustomizationButton(onClick = {
        TextCustomization.appendAroundSelected(textState, "*")
    }) { Text("I", fontStyle = FontStyle.Italic) }
    CustomizationButton(onClick = {
        // underline text with <ins> </ins>
        TextCustomization.appendAroundSelected(textState, "<u>", "</u>")
    }) { Text("U", style = TextStyle(textDecoration = TextDecoration.Underline)) }
    CustomizationButton(onClick = {
        TextCustomization.appendAroundSelected(textState, "~~")
    }) {
        Text( text = "S", style = TextStyle(textDecoration = TextDecoration.LineThrough))
    }
    Divider(
        color = Color.Gray,
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .width(1.dp)
    )
    codeBlockButton(textState)
}

@Composable
fun InsertImage(textState: MutableState<TextFieldValue>) {
    var isFileChooserOpen by remember { mutableStateOf(false) }
    Button(onClick = {
        isFileChooserOpen = true
    }) {
        Text("Insert image")
    }
    if (isFileChooserOpen) {
        ImageDialog(
            onCloseRequest = {
                isFileChooserOpen = false
                if (it != null && File(it).exists()) {
                    FileIO.duplicateFile(File(it))
                    TextCustomization.insertImageTag(textState, it)
                }
            }
        )
    }
}

@Composable
fun TopBar(textState: MutableState<TextFieldValue>) {
    TopAppBar(
        title = {
            Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                TextCustomizationMenu(textState)
                InsertImage(textState)
            }
        },
    )
}