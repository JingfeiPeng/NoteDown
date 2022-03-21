package business.markdown

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CommonMarkEmitterTest {

    private fun String.addNewline(): String {
        return this + "\n"
    }

    @Test
    fun emitFromBasic() {
        val testMarkdown = """
            ~~**STRUCK BOLD**~~
            # HEADER
            Paragraph without stuff
        """.trimIndent().addNewline()

        val expectedHtml = """
            <p><s><strong>STRUCK BOLD</strong></s></p>
            <h1>HEADER</h1>
            <p>Paragraph without stuff</p>
        """.trimIndent().addNewline()

        val emitter = CommonMarkEmitter()
        val emittedHtml = emitter.emitFrom(testMarkdown)

        assertEquals(expectedHtml, emittedHtml)
    }
}