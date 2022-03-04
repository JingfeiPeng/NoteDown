import org.junit.jupiter.api.*
import java.io.File

import persistence.DocumentMetaCRUDJson
import java.util.*
import kotlin.test.*
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DocumentMetaCRUDJsonTest {

    val basePath =  File(System.getProperty("user.home")+"/NotesTaker")
    val testingFolder = File(basePath,"testingTestingTestingFolderNAKSJDNsa")

    @BeforeAll
    fun setup() {
        if (!basePath.exists()) {
            basePath.mkdir()
        }
        testingFolder.mkdir()
    }

    @AfterAll
    fun cleanup() {
        testingFolder.delete()
    }

    @Test
    fun testCreateDocumentMetaData() {
        // arrange
        val name = "randomFile"

        // act
        DocumentMetaCRUDJson.createDocumentMetaData(testingFolder, name)

        // assert
        val createdMetaFile = File(testingFolder, "$name.json")
        assertEquals(true, createdMetaFile.exists(), )

        // cleanup
        createdMetaFile.delete()
    }

    @Test
    fun testReadAllMetaData() {
        // arrange
        val names = arrayOf("randomFile1","randomFile2")

        // act
        for (name in names) {
            DocumentMetaCRUDJson.createDocumentMetaData(testingFolder, name)
        }
        val docs = DocumentMetaCRUDJson.readAllMetaData()

        // assert
        val checks = arrayOf(false, false)
        assertEquals(docs.size >= names.size, true)
        for (doc in docs) {
            names.forEachIndexed { index,name ->
                if (doc.path == "${testingFolder.name}/${name}") {
                    checks[index] = true
                    // the files were created just now
                    assertEquals(Math.abs(doc.createdOn-Date().time) <= 1000, true)
                }
            }
        }
        for (check in checks) {
            assertEquals(true, check)
        }

        // cleanup
        for (name in names) {
            File(testingFolder, "$name.json").delete()
        }
    }
}