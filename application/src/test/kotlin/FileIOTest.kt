import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import data.NoteFile
import data.NoteFolder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import persistence.FileIO
import java.io.File

import org.mockito.Mock;
import kotlin.test.*


internal class FileIOTest {

    private val file = File("src/test/test.txt")
    private val noteFile = NoteFile(file)

    private val basePath =  System.getProperty("user.home")+"/NotesTaker"

    @BeforeEach
    fun setup() {
        if (!File(basePath).exists()) {
            File(basePath).mkdir()
        }
    }

    @Test
    fun testWriteToFile() {
        // arrange
        val text = "Save this text"

        // act
        FileIO.saveText(text, noteFile)

        // assert
        assertEquals(file.readText(), text)
    }

    @Test
    fun testReadFile() {
        // arrange
        val text = mutableStateOf(TextFieldValue())
        file.writeText("Hello")

        // act
        FileIO.loadFile(text, noteFile)

        // assert
        assertEquals(text.value.text, "Hello")
    }

    @Test
    fun testReadNotesFolder() {
        // arrange
        val testingFolder = File("$basePath/IMPOSSIBLE_RANDOMFOLDER_AJSNdioasidasbiud")
        testingFolder.mkdir()
        val testingFile = File("$basePath/IMPOSSIBLE_RANDOMFOLDER_AJSNdioasidasbiud/somefile.txt")
        testingFile.createNewFile()

        // act
        val res = FileIO.readNotesFolder()

        // assert
        val expectedNoteFolder = NoteFolder(testingFolder)
        expectedNoteFolder.addChild(NoteFile(testingFile))
        res.contains(expectedNoteFolder)

        // cleanup
        testingFile.delete()
        testingFolder.delete()
    }

    @Test
    fun testMakeFolder() {
        // Arrange
        val name = "IMPOSSIBLE_RANDOMFOLDER_AJSNdioasidasbiudgkfngfkndngk"

        // Act
        val folderNode = FileIO.makeFolder(name)

        // Assert
        assertNotNull(folderNode)
        assertEquals(name, folderNode.name)
        assertTrue(folderNode.file.isDirectory)
        assertEquals(File(basePath).absolutePath, File(folderNode.file.parent).absolutePath)
        assertEquals(name, folderNode.file.name)

        // Cleanup
        folderNode.file.delete()
    }


    @Test
    fun testMakeFile() {
        // arrange
        val testingFolder = File("$basePath/IMPOSSIBLE_RANDOMFOLDER_AJSNdioasidasbiud")
        testingFolder.mkdir()
        val noteFolder = NoteFolder(testingFolder)
        val name = "somefile"

        // Act
        val (folderNode, fileNode) = FileIO.makeFile(noteFolder, name)

        // Assert
        assertNotNull(fileNode)
        assertNotNull(folderNode)
        assertContains(folderNode.children, fileNode)
        assertEquals("$name.md", fileNode.name)
        assertTrue(fileNode.file.isFile)
        assertEquals(folderNode.file.absolutePath, File(fileNode.file.parent).absolutePath)
        assertEquals("$name.md", fileNode.file.name)

        // Cleanup
        fileNode.file.delete()
        File(testingFolder, "$name.json").delete()
        testingFolder.delete()
    }

    @AfterEach
    fun cleanup() {
        if (file.exists()) {
            file.delete()
        }
    }
}