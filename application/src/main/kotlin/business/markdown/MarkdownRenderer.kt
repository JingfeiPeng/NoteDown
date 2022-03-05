package business.markdown

import org.jsoup.nodes.Element

interface Emitter<in DataType, out EmittedType> {
    fun emitFrom(from: DataType): EmittedType
}

interface Renderer<in RenderData> {
    fun render(from: RenderData)
}

abstract class EmissionRenderer<in DataType, EmittedType>(
    private val emitter: Emitter<DataType, EmittedType>,
    private val renderer: Renderer<EmittedType>
) : Renderer<DataType> {
    override fun render(from: DataType) {
        val emission = emitter.emitFrom(from)
        renderer.render(emission)
    }
}

interface MarkdownHtmlEmitter : Emitter<String, String>
interface HtmlTreeEmitter : Emitter<String, Element>

interface MarkdownRenderer : Renderer<String>
interface HtmlRenderer : Renderer<String>
interface HtmlTreeRenderer : Renderer<Element>

class MarkdownHtmlRenderer(htmlEmitter: MarkdownHtmlEmitter, htmlRenderer: HtmlRenderer) :
    MarkdownRenderer, EmissionRenderer<String, String>(htmlEmitter, htmlRenderer)
class HtmlParsingRenderer(treeEmitter: HtmlTreeEmitter, treeRenderer: HtmlTreeRenderer) :
    HtmlRenderer, EmissionRenderer<String, Element>(treeEmitter, treeRenderer)

