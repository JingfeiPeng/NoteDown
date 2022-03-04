package business.markdown

import org.jsoup.nodes.Document

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

interface MarkdownHTMLEmitter : Emitter<String, String>
interface HTMLTreeEmitter : Emitter<String, Document>

interface MarkdownRenderer : Renderer<String>
interface HTMLRenderer : Renderer<String>
interface HTMLTreeRenderer : Renderer<Document>

class MarkdownHTMLRenderer(htmlEmitter: MarkdownHTMLEmitter, htmlRenderer: HTMLRenderer) :
    EmissionRenderer<String, String>(htmlEmitter, htmlRenderer)
class HTMLParsingRenderer(treeEmitter: HTMLTreeEmitter, treeRenderer: HTMLTreeRenderer) :
    EmissionRenderer<String, Document>(treeEmitter, treeRenderer)


