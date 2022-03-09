package business.markdown

import org.jsoup.nodes.Element
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MarkdownHtmlRendererTest {
    @Test
    fun testMarkdownHtmlRenderer() {
        val inputText = "IGNORE ME"
        val outputHtml = "I SHOULD BE RENDERED"

        val mockMarkdownEmitter = object : MarkdownHtmlEmitter {
            override fun emitFrom(from: String): String {
                assertEquals(inputText, from)
                return outputHtml
            }
        }

        val mockHtmlRenderer = object : HtmlRenderer {
            override fun render(from: String) = assertEquals(outputHtml, from)
        }

        val renderer = MarkdownHtmlRenderer(mockMarkdownEmitter, mockHtmlRenderer)

        renderer.render(inputText)
    }

    @Test
    fun testHtmlParsingRenderer() {
        val inputHtml = "IGNORE ME"
        val outputElement = Element("html").appendText("TEST")

        val mockHtmlTreeEmitter = object : HtmlTreeEmitter {
            override fun emitFrom(from: String): Element {
                assertEquals(inputHtml, from)
                return outputElement
            }
        }

        val mockHtmlTreeRenderer = object : HtmlTreeRenderer {
            override fun render(from: Element) {
                assertEquals(outputElement.tag(), from.tag())
                assertEquals(outputElement.childrenSize(), from.childrenSize())
                assertEquals(outputElement.text(), from.text())
            }
        }

        val renderer = HtmlParsingRenderer(mockHtmlTreeEmitter, mockHtmlTreeRenderer)

        renderer.render(inputHtml)
    }
}