package presentation.markdown

import business.markdown.*

enum class MarkdownRenderers(val displayString: String, val renderFun: (ComposableConsumer) -> MarkdownRenderer) {
    RAW_MARKDOWN("Raw Markdown", ::RawMarkdownRenderer),
    RAW_HTML("Raw HTML", { composableConsumer ->
        MarkdownHtmlRenderer(
            CommonMarkEmitter(),
            RawHtmlRenderer(composableConsumer)
        )
    }),
    MIKE_PENZ_MARKDOWN("Mike Penz's", ::MikePenzMarkdownRenderer),
    FULL_MARKDOWN("Full Markdown", { composableConsumer ->
        MarkdownHtmlRenderer(
            CommonMarkEmitter(),
            HtmlParsingRenderer(JsoupEmitter(), HtmlTreeComposeRenderer(composableConsumer))
        )
    }),
    SWING_BROWSER_MARKDOWN("Swing Browser Markdown", { composableConsumer ->
        MarkdownHtmlRenderer(
            CommonMarkEmitter(),
            SwingBrowserComposeRenderer(composableConsumer)
        )
    })
}