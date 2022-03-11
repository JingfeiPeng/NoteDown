package presentation.markdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import business.markdown.HtmlRenderer
import javax.swing.JEditorPane
import javax.swing.JScrollBar
import javax.swing.JScrollPane
import javax.swing.SwingUtilities
import javax.swing.text.html.HTMLEditorKit


class SwingBrowserComposeRenderer(composableConsumer: ComposableConsumer) : HtmlRenderer,
    ComposableRenderer<String>(composableConsumer) {

    private val scrollPane = JScrollPane()
    private val swingPane = JEditorPane()
    init {
        swingPane.contentType = "text/html"
        swingPane.isEditable = false

        scrollPane.add(swingPane)
    }

    @Composable
    override fun composableRender(from: String) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            SwingPanel(factory = {
                swingPane
            })
        }

        SideEffect {
            swingPane.text = from
        }
    }
}