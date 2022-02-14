package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.NoteFile
import data.NoteFolder

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DocumentSelectionArea(folders: List<NoteFolder>) {
    val selectedFolder = remember { mutableStateOf<NoteFolder?>(null) }
    val selectedFile = remember { mutableStateOf<NoteFile?>(null) }

    val selectedColour = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    val unselectedColour = ButtonDefaults.buttonColors(backgroundColor = Color.White)

    Row(Modifier.fillMaxSize().padding(top = 10.dp)) {
        Box(modifier = Modifier.fillMaxWidth(0.5f)) {
            Column {
                folders.forEach {
                    val colour = if (selectedFolder.value == it) selectedColour else unselectedColour
                    CompositionLocalProvider(
                        LocalMinimumTouchTargetEnforcement provides false,
                    ) {
                        TextButton(modifier = Modifier.fillMaxWidth(), shape = RectangleShape, onClick = {
                            if (selectedFolder.value != it) {
                                selectedFolder.value = it
                                it.children.firstOrNull()?.let { file ->
                                    selectedFile.value = file
                                }
                            }
                        }, colors = colour) {
                            Text(it.name, textAlign = TextAlign.Left)
                        }
                    }
                }
            }
        }

        Divider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Box {
            selectedFolder.value?.let {
                Column {
                    it.children.forEach {
                        val colour = if (selectedFile.value == it) selectedColour else unselectedColour
                        CompositionLocalProvider(
                            LocalMinimumTouchTargetEnforcement provides false,
                        ) {
                            TextButton(
                                modifier = Modifier.fillMaxWidth().height(40.dp),
                                shape = RectangleShape,
                                onClick = {
                                    selectedFile.value = it
                                },
                                colors = colour,
                            ) {
                                Text(it.name)
                            }
                        }
                    }
                }
            }
        }
    }
}
