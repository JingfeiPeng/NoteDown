package presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.NoteEntry
import data.NoteFile
import data.NoteFolder

@Composable
fun DocumentSelectionArea(tree: NoteEntry, prefix: String = "") {
    when (tree) {
        is NoteFile -> Text("${prefix}-> ${tree.name}")
        is NoteFolder -> {
            Text("${prefix}-> ${tree.name}")
            tree.children.forEach {
                DocumentSelectionArea(it, prefix = "$prefix    ")
            }
        }
    }
}
