package presentation

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable


@Composable
fun TextCustomizationMenu() {
    TopAppBar(
        title = {
            Text("Text Customization Menu")
        },
    )
}