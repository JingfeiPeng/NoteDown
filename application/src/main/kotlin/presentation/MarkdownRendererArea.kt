package presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import business.markdown.MarkdownRenderer
import presentation.markdown.ComposableConsumer
import presentation.markdown.ComposeRenderable
import presentation.markdown.MarkdownRenderers

@Composable
fun MarkdownRendererArea(
    textState: MutableState<TextFieldValue>,
    rendererFun: (ComposableConsumer) -> MarkdownRenderer = MarkdownRenderers.RAW_MARKDOWN.renderFun
) {
    var renderable: ComposeRenderable? = null
    val renderer = rendererFun { outputRenderable ->
        renderable = outputRenderable
    }

    renderer.render(textState.value.text)
    renderable?.also { it.composeRender() } ?:
        Text("Unable to render markdown", color = Color.Red)
}