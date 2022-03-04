package persistence

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import data.NoteFile
import data.NoteFolder
import presentation.updateSelectedFile
import java.io.File


class FileIO {
    companion object {
        private val notesFolder = System.getProperty("user.home") + "/NotesTaker"

        fun saveText(text: String, file: NoteFile?) {
            file?.let { file.file.writeText(text) }
        }

        fun loadFile(textState: MutableState<TextFieldValue>, file: NoteFile) {
            textState.value = TextFieldValue(file.file.readText())
        }

        fun makeFolder(name: String): NoteFolder? {
            val notesDirectory = File(notesFolder)
            val newFolder = File(notesDirectory, name)

            if (!newFolder.exists()) {
                newFolder.mkdirs()
            }

            return readNotesFolder().find { it.name == name }
        }

        fun makeFile(dir: NoteFolder, name: String): Pair<NoteFolder?, NoteFile?> {
            if (name == null || name == "") {
                return Pair(null, null)
            }
            // append .md extension
            val nameWithExtension = "$name.md"
            val newFile = File(dir.file, nameWithExtension)
            if (!newFile.exists()) {
                newFile.createNewFile()
            }

            val noteFolder = readNotesFolder().find { it.name == dir.name }
            val noteFile = noteFolder?.children?.find {
                it.name == nameWithExtension
            }

            // store meta data
            DocumentMetaCRUD.createDocumentMetaData(
                dir.file, name
            )

            return Pair(noteFolder, noteFile)
        }

        fun readNotesFolder(extension: String = "md"): ArrayList<NoteFolder> {
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
                } else if (it.extension == extension) {
                    // only show files that match the input extension param
                    foldersAndFiles.last().addChild(NoteFile(it))
                }
            }

            return foldersAndFiles
        }
    }
}