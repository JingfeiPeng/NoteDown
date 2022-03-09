package business.markdown

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import presentation.markdown.ComposableConsumer
import presentation.markdown.ComposableRenderer

abstract class RawRenderer(composableConsumer: ComposableConsumer) : Renderer<String>, ComposableRenderer<String>(composableConsumer) {
    @Composable
    override fun composableRender(from: String) = Text(from)
}

class RawMarkdownRenderer(composableConsumer: ComposableConsumer) : MarkdownRenderer, RawRenderer(composableConsumer)
class RawHtmlRenderer(composableConsumer: ComposableConsumer) : HtmlRenderer, RawRenderer(composableConsumer)