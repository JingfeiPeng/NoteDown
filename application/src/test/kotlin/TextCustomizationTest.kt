import kotlin.test.Test
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import business.TextCustomization
import kotlin.test.assertEquals


internal class TextCustomizationTest {

    @Test
    fun testAppendAroundSelected_add() {
        // arrange
        val startRange = 7
        val endRange = 11
        val textState =  mutableStateOf(TextFieldValue(
            "Hello, this is a text document\nHere",
            TextRange(startRange,endRange)
        ))
        val selectedChars = textState.value.text.substring(startRange, endRange)
        val frontChars = "random_stuff"
        val backChars = "Rand_aaa"

        // act
        TextCustomization.Companion.appendAroundSelected(
            textState,
            frontChars,
            backChars
        )

        // test
        assertEquals(
            textState.value.text.substring(startRange, startRange+frontChars.length),
            frontChars
        )
        assertEquals(
            textState.value.text.substring(startRange+frontChars.length, endRange+frontChars.length),
            selectedChars,
        )
        assertEquals(
            textState.value.text.substring(endRange+frontChars.length, endRange+frontChars.length+backChars.length),
            backChars
        )
        assertEquals(
            textState.value.text,
            "Hello, random_stuffthisRand_aaa is a text document\nHere",
        )
    }

    @Test
    fun testAppendAroundSelected_delete() {
        // arrange
        val startRange = 7
        val endRange = 11
        val frontChars = "FRONT_CHARS"
        val backChars = "BACK_CHARS"
        val textState =  mutableStateOf(TextFieldValue(
            "Hello, FRONT_CHARSthisBACK_CHARS is a text document\nHere",
            TextRange(startRange+frontChars.length, frontChars.length+endRange)
        ))

        // act
        TextCustomization.Companion.appendAroundSelected(
            textState,
            frontChars,
            backChars
        )

        // test
        assertEquals(
            "Hello, this is a text document\nHere",
            textState.value.text
        )
    }

    @Test
    fun testinsertImageTag() {
        // arrange
        val startRange = 6
        val textState =  mutableStateOf(TextFieldValue(
            "Hello, this is a text document\nHere",
            TextRange(startRange)
        ))

        // act
        TextCustomization.Companion.insertImageTag(
            textState,
            "test.jpg"
        )

        // test
        assertEquals(
            "Hello,<img src=\"file:test.jpg\"> this is a text document\nHere",
            textState.value.text
        )
    }
}
