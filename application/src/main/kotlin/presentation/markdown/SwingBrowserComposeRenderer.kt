package presentation.markdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import business.markdown.HtmlRenderer
import java.awt.Desktop
import javax.swing.JEditorPane
import javax.swing.JScrollPane
import javax.swing.event.HyperlinkEvent
import javax.swing.text.DefaultCaret





class SwingBrowserComposeRenderer(composableConsumer: ComposableConsumer) : HtmlRenderer,
    ComposableRenderer<String>(composableConsumer) {

    @Composable
    override fun composableRender(from: String) {
        val swingPane = remember {
            JEditorPane().apply {
                contentType = "text/html"
                isEditable = false
                addHyperlinkListener {
                    if (it.eventType != HyperlinkEvent.EventType.ACTIVATED) {
                        return@addHyperlinkListener
                    }

                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(it.url.toURI())
                    }
                }
                (caret as DefaultCaret).updatePolicy = DefaultCaret.NEVER_UPDATE
            }
        }

        val scrollPane = remember {
            JScrollPane(swingPane)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SwingPanel(factory = {
                scrollPane
            }, update = {
                swingPane.text = from
            })
        }
    }
}