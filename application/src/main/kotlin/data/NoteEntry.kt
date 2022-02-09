package data

sealed interface NoteEntry
data class NoteFile(val name: String) : NoteEntry
data class NoteFolder(val name: String, val children: List<NoteEntry>) : NoteEntry