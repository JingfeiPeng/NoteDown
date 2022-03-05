package presentation.markdown

import androidx.compose.runtime.Composable
import business.markdown.MarkdownRenderer
import com.mikepenz.markdown.Markdown

class MikePenzMarkdownRenderer(composableConsumer: ComposableConsumer) : MarkdownRenderer, ComposableRenderer<String>(composableConsumer) {
    @Composable
    override fun composableRender(from: String) {
        Markdown(from)
    }
}
