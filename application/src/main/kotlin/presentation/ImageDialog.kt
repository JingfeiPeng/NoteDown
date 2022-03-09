package presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.AwtWindow
import java.awt.FileDialog
import java.awt.Frame
import java.io.FilenameFilter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageDialog(
    parent: Frame? = null,
    onCloseRequest: (result: String?) -> Unit
) = AwtWindow(
    create = {
        object : FileDialog(parent, "Choose a file", LOAD) {
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

            override fun setFilenameFilter(filter: FilenameFilter?) {
                super.setFilenameFilter(filter)
            }
        }
    },
    dispose = FileDialog::dispose
)