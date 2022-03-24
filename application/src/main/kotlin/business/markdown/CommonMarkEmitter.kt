package business.markdown

import com.codewaves.codehighlight.core.Highlighter
import org.commonmark.Extension
import org.commonmark.ext.gfm.strikethrough.Strikethrough
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.node.Document
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.html.HtmlWriter

private class StrikethroughHtmlNodeRendererUTag(private val context: HtmlNodeRendererContext) : NodeRenderer {
    private val html: HtmlWriter = context.writer

    override fun getNodeTypes(): Set<Class<out Node?>> {
        return setOf<Class<out Node?>>(Strikethrough::class.java)
    }

    override fun render(node: Node) {
        val attributes = context.extendAttributes(node, "s", emptyMap())
        html.tag("s", attributes)
        renderChildren(node)
        html.tag("/s")
    }

    private fun renderChildren(parent: Node) {
        var node = parent.firstChild
        while (node != null) {
            val next = node.next
            context.render(node)
            node = next
        }
    }
}

private class HighlightedCodeBlockNodeRenderer(context: HtmlNodeRendererContext) : NodeRenderer {
    private val html: HtmlWriter = context.writer
    private val highlighter = Highlighter { com.codewaves.codehighlight.renderer.HtmlRenderer("hljs-") }

    override fun getNodeTypes(): Set<Class<out Node?>> {
        return setOf<Class<out Node?>>(FencedCodeBlock::class.java)
    }

    override fun render(node: Node) {
        val codeBlock = node as FencedCodeBlock

        val result: Highlighter.HighlightResult = highlighter.highlight(codeBlock.info, codeBlock.literal)

        html.line()
        html.tag("pre")
        html.tag("code")
        html.raw(result.result.toString())
        html.tag("/code")
        html.tag("/pre")
        html.line()
    }
}

class CommonMarkEmitter(extensions: List<Extension> = listOf(StrikethroughExtension.create())) : MarkdownHtmlEmitter {
    private val parser = Parser.builder().extensions(extensions).build()
    private val renderer = HtmlRenderer.builder()
        .nodeRendererFactory(::StrikethroughHtmlNodeRendererUTag)
        .nodeRendererFactory(::HighlightedCodeBlockNodeRenderer)
        .extensions(extensions)
        .build()

    override fun emitFrom(from: String): String {
        val document = parser.parse(from) as Document
        return renderer.render(document)
    }
}
