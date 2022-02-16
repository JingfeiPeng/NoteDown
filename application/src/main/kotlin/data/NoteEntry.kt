package data

import java.io.File

data class NoteFile(val file: File) {
    val name: String get() = this.file.name
}
data class NoteFolder(
    val file: File,
    val children: ArrayList<NoteFile> = ArrayList()
    ) {

    val name: String get() = this.file.name

    fun addChild(child: NoteFile) {
        children.add(child)
    }
}