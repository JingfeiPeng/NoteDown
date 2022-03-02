package presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.Markdown

@Composable
fun MarkdownRendererArea(textState: MutableState<TextFieldValue>) {
    val scrollState = rememberScrollState()

    Markdown(textState.value.text, Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState))
}
