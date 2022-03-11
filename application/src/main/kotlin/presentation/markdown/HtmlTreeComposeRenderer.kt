package presentation.markdown

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import business.markdown.HtmlTreeRenderer
import org.jsoup.nodes.Element

class HtmlTreeComposeRenderer(composableConsumer: ComposableConsumer) : HtmlTreeRenderer,
    ComposableRenderer<Element>(composableConsumer) {
    @Composable
    override fun composableRender(from: Element) {
        Column {
            when (from.tag().name) {
                "body" -> renderChildren(from.children().toList())
                else -> Text("Unknown HTML tag ${from.tag().name}")
            }
        }
    }

    @Composable
    fun renderChildren(elements: List<Element>) = elements.forEach {
        Text(it.toString())
    }
}