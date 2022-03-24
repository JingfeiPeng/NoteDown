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
import javax.swing.text.html.HTMLEditorKit

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

                val htmlEditorKit = (editorKit as HTMLEditorKit)

                val colourMapping = mapOf(
                    Pair("keyword", "blue"),
                    Pair("number", "blue"),
                    Pair("function", "#00627A"),
                    Pair("attr", "purple"),
                    Pair("title", "#00627A"),
                    Pair("string", "green")
                )

                htmlEditorKit.styleSheet.addRule("body { font-family: Roboto; font-size: 16pt; }")
                htmlEditorKit.styleSheet.addRule("h1 { font-size: 24pt; }")
                htmlEditorKit.styleSheet.addRule("h2 { font-size: 20pt; }")
                htmlEditorKit.styleSheet.addRule("h3 { font-size: 18pt; }")
                htmlEditorKit.styleSheet.addRule("h4 { font-size: 16pt; }")
                htmlEditorKit.styleSheet.addRule("h5 { font-size: 16pt; }")
                htmlEditorKit.styleSheet.addRule("h6 { font-size: 16pt; }")
                htmlEditorKit.styleSheet.addRule("code { font-size: 14pt; background-color: #CCCCCC; }")


                colourMapping.forEach {
                    htmlEditorKit.styleSheet.addRule(".hljs-${it.key} {color: ${it.value};}")
                }
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