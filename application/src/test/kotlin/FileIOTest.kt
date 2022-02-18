import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import data.NoteFile
import data.NoteFolder
import org.junit.jupiter.api.AfterEach
import persistence.FileIO
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

import org.mockito.Mock;


internal class FileIOTest {

    private val file = File("src/test/test.txt")
    private val noteFile = NoteFile(file)

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
        val basePath =  System.getProperty("user.home")+"/NotesTaker"
        if (!File(basePath).exists()) {
            File(basePath).mkdir()
        }
        File(basePath).mkdir()
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

    @AfterEach
    fun cleanup() {
        if (file.exists()) {
            file.delete()
        }
    }
}