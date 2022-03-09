package business.markdown

import org.commonmark.Extension
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.node.Document
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

class CommonMarkEmitter(extensions: List<Extension> = listOf(StrikethroughExtension.create())) : MarkdownHtmlEmitter {
    private val parser = Parser.builder().extensions(extensions).build()
    private val renderer = HtmlRenderer.builder().extensions(extensions).build()

    override fun emitFrom(from: String): String {
        val document = parser.parse(from) as Document
        return renderer.render(document)
    }
}
