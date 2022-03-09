package presentation.markdown

import androidx.compose.runtime.Composable
import business.markdown.Renderer

interface ComposeRenderable {
    @Composable
    fun composeRender()
}

typealias ComposableConsumer = (ComposeRenderable) -> Unit

abstract class ComposableRenderer<T>(private val composableConsumer: ComposableConsumer) : Renderer<T> {

    @Composable
    abstract fun composableRender(from: T)

    override fun render(from: T) {
        val renderable = object : ComposeRenderable {
            @Composable
            override fun composeRender() {
                composableRender(from)
            }
        }
        composableConsumer(renderable)
    }
}