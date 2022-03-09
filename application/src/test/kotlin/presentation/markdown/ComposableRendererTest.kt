package presentation.markdown

import androidx.compose.runtime.Composable
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class ComposableRendererTest {
    @Test
    fun testMarkdownHtmlRenderer() {
        var output: ComposeRenderable? = null
        val composableRenderer = object : ComposableRenderer<String>({ output = it }) {
            @Composable
            override fun composableRender(from: String) {}
        }

        composableRenderer.render("INPUT")

        // Can't check that the output actually runs the ComposableRenderer without UI mocks
        assertNotNull(output)
    }
}