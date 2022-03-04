package persistence

import database.Document
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.util.*

object DocumentMetaCRUDJson : DocumentMetaCRUD {
    override fun createDocumentMetaData(folder: File, name: String) {
        val string = Json.encodeToString(Document("${folder.name}/$name", Date()))
        val f = File(folder, "$name.json")
        f.writeText(string)
    }

    // can perform CRUD operations on the arraylist of documents returned
    override fun readAllMetaData(): ArrayList<Document> {
        val folders =  FileIO.readNotesFolder("json")

        val documents = ArrayList<Document>()

        folders.forEach{
            it.children.forEach{
                documents.add(Json.decodeFromString<Document>(it.file.readText()))
            }
        }
        return documents
    }
}
