package business.markdown

import org.commonmark.Extension
import org.commonmark.ext.gfm.strikethrough.Strikethrough
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.node.Document
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.html.HtmlRenderer.HtmlRendererExtension
import org.commonmark.renderer.html.HtmlWriter

internal abstract class StrikethroughNodeRendererUTag : NodeRenderer {
    override fun getNodeTypes(): Set<Class<out Node?>> {
        return setOf<Class<out Node?>>(Strikethrough::class.java)
    }
}

class StrikethroughExtensionUTag private constructor() : HtmlRendererExtension {

    override fun extend(rendererBuilder: HtmlRenderer.Builder) {
        rendererBuilder.nodeRendererFactory { context -> StrikethroughHtmlNodeRendererUTag(context) }
    }

    companion object {
        fun create(): Extension {
            return StrikethroughExtensionUTag()
        }
    }
}

private class StrikethroughHtmlNodeRendererUTag(private val context: HtmlNodeRendererContext) : StrikethroughNodeRendererUTag() {
    private val html: HtmlWriter = context.writer

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


class CommonMarkEmitter(extensions: List<Extension> = listOf(StrikethroughExtensionUTag.create(), StrikethroughExtension.create())) : MarkdownHtmlEmitter {
    private val parser = Parser.builder().extensions(extensions).build()
    private val renderer = HtmlRenderer.builder().extensions(extensions).build()

    override fun emitFrom(from: String): String {
        val document = parser.parse(from) as Document
        return renderer.render(document)
    }
}
