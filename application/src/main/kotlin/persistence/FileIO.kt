package persistence

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import business.UserFiles
import data.NoteFile
import data.NoteFolder
import database.Document
import database.UserFile
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


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

        fun deleteFile(file: NoteFile) {
            file.file.delete()
            File(file.file.absolutePath.split(".md")[0]+".json").delete()
        }

        fun deleteFolder(folder: NoteFolder) {
            folder.file.deleteRecursively()
        }

        fun makeFile(dir: NoteFolder, name: String): Pair<NoteFolder?, NoteFile?> {
            if (name == "") {
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
            DocumentMetaCRUDJson.createDocumentMetaData(
                dir.file, name
            )

            return Pair(noteFolder, noteFile)
        }

        fun getAllUserFiles(): List<UserFile> {
            val userId = User().getCurrentUserId()
            val res = ArrayList<UserFile>()
            val noteFolders = readNotesFolder()
            for (folder in noteFolders) {
                for (file in folder.children) {
                    val metaData = DocumentMetaCRUDJson.readMetaDataByFile(folder.name, file.name)
                    res.add(UserFile(
                        userId = userId,
                        fileName = file.name,
                        folderName = folder.name,
                        createdOn = metaData.createdOn.time,
                        text = file.file.readText()
                    ))
                }
            }
            return res.toList()
        }

        fun emptyAndRepopulateFiles(files: List<UserFile>) {
            File(notesFolder).walk().forEach {
                if (it.compareTo(File(notesFolder)) == 0) {
                    return@forEach
                }
                if (it.isDirectory) {
                    it.deleteRecursively()
                }
            }
            var folderName = ""
            for (file in files) {
                if (file.folderName != folderName) {
                    val folder = File(notesFolder, file.folderName)
                    if (!folder.exists()) {
                        folder.mkdir()
                    }
                }
                val noteFile = File("$notesFolder/${file.folderName}/${file.fileName}")
                noteFile.writeText(file.text)

                // recreate meta data
                val metaPath = file.fileName.split(".")[0]+".json"
                val metaFile = File("$notesFolder/${file.folderName}/$metaPath")
                val metaData = Document(
                    file.folderName+"/"+file.fileName.split(".")[0],
                    Date(file.createdOn),
                )
                metaFile.writeText(Json.encodeToString(metaData))
            }
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

        fun duplicateFile(f: File) {
            // to-do: move the file from given location to a location where it can be displayed
        }
    }
}