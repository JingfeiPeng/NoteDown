package presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.AwtWindow
import java.awt.FileDialog
import java.awt.Frame


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageDialog(
    parent: Frame? = null,
    onCloseRequest: (result: String?) -> Unit
) = AwtWindow(
    create = {
        object : FileDialog(parent, "Choose a file", LOAD) {
            init {
                // only choose image files
                setFile("*.jpg;*.jpeg")
            }
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    if (file != null && directory != null) {
                        onCloseRequest("$directory$file")
                    } else {
                        onCloseRequest(null)
                    }
                }
            }
        }
    },
    dispose = FileDialog::dispose
)