package presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue




// button text placement went out of the button
@Composable
fun CustomizationButton(onClick: () -> Unit, content: @Composable() () -> Unit) {
    val buttonColors = ButtonDefaults.buttonColors(contentColor = Color.White)
    val buttonWidth = 40.dp
    Button(
        onClick = {},
        colors = buttonColors,
        modifier = Modifier.width(buttonWidth),
    ) {
        content()
    }
}

@Composable
fun codeBlockButton() {
    Divider(
        color = Color.Gray,
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .width(1.dp)
    )
    // need a space for margin
    Text(" ")
    var expanded by remember { mutableStateOf(false) }

    val items = listOf("JavaScript", "Python", "Java")
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
                    System.out.println(items.get(index))
                }) {
                    Text(text = s)
                }
            }
        }
    }
}



@Composable
fun TextCustomizationMenu() {
    TopAppBar(
        title = {
            Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.fillMaxWidth(0.20f)) {
                    Text(
                        text = "Text Customization Menu",
                        style = MaterialTheme.typography.body1
                    )
                }
                CustomizationButton(onClick = {}) { Text("B", fontWeight=FontWeight.Bold) }
                CustomizationButton(onClick = {}) { Text("I", fontStyle = FontStyle.Italic) }
                CustomizationButton(onClick = {
                    // underline text with <ins> </ins>
                }) { Text("U", style = TextStyle(textDecoration = TextDecoration.Underline)) }
                CustomizationButton(onClick = {
                    // strikethrough with ~~TEXT~~
                }) {
                    Text( text = "S", style = TextStyle(textDecoration = TextDecoration.LineThrough))
                }
                codeBlockButton()
            }
        },
    )
}