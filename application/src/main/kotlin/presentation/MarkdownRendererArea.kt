package presentation

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.node.Document
import org.commonmark.node.Node
import org.commonmark.node.Text
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

@Composable
fun MarkdownRendererArea(textState: MutableState<TextFieldValue>) {
    val scrollState = rememberScrollState()

    val extensions = listOf(StrikethroughExtension.create())
    val parser = Parser.builder().extensions(extensions).build()
    val document = parser.parse(textState.value.text) as Document
    val html = HtmlRenderer.builder().extensions(extensions).build().render(document)

    Text(html)
//    Markdown(textState.value.text, Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState))
}
