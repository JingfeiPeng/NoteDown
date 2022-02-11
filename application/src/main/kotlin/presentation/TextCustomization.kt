package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp


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
            }
        },
    )
}