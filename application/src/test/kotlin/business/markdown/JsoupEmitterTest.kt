package business.markdown

import org.jsoup.nodes.Element
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class JsoupEmitterTest {

    private fun String.addNewline(): String {
        return this + "\n"
    }

    private fun Element.assertSameStructure(other: Element) {
        assertEquals(tag(), other.tag())
        assertEquals(text(), other.text())
        assertEquals(childrenSize(), other.childrenSize())
        children().zip(other.children()) { first, second ->
            first.assertSameStructure(second)
        }
    }

    @Test
    fun emitFromBasic() {
        val testHtml = """
            <p><del><strong>STRUCK BOLD</strong></del></p>
            <h1>HEADER</h1>
            <p>Paragraph without stuff</p>
        """.trimIndent().addNewline()

        val expectedTree = Element("body")
            .appendChildren(listOf(
                Element("p")
                    .appendChild(Element("del")
                        .appendChild(Element("strong").appendText("STRUCK BOLD"))),
                Element("h1").appendText("HEADER"),
                Element("p").appendText("Paragraph without stuff")))

        val emitter = JsoupEmitter()
        val emittedTree = emitter.emitFrom(testHtml)

        expectedTree.assertSameStructure(emittedTree)
    }
}