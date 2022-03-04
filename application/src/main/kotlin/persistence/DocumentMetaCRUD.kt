package persistence

import database.Document
import java.io.File
import java.util.ArrayList

interface DocumentMetaCRUD {
    fun createDocumentMetaData(folder: File, name: String)
    fun readAllMetaData(): ArrayList<Document>
}