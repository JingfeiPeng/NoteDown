package persistence

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import data.NoteFile
import data.NoteFolder
import java.io.File


class FileIO {
    companion object {
        private val notesFolder = System.getProperty("user.home")+"/NotesTaker"

        fun saveText(text: String, file: NoteFile?) {
            file?.let { file.file.writeText(text) }
        }

        fun loadFile(textState: MutableState<TextFieldValue>, file: NoteFile) {
            textState.value = TextFieldValue(file.file.readText())
        }

        fun readNotesFolder() : ArrayList<NoteFolder> {
            val notesDirectory = File(notesFolder)
            if (!File(notesFolder).exists()) {
                notesDirectory.mkdir()
            }

            val foldersAndFiles = ArrayList<NoteFolder>()

            File(notesFolder).walk().forEach {
                if (it.compareTo(File(notesFolder)) == 0) {
                    return@forEach
                }
                if (it.isDirectory) {
                    foldersAndFiles.add(NoteFolder(it))
                } else {
                    foldersAndFiles.last().addChild(NoteFile(it))
                }
            }

            return foldersAndFiles
        }
    }
}