package data

data class NoteFile(val name: String)
data class NoteFolder(val name: String, val children: List<NoteFile>)