package business

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.text.input.getTextAfterSelection
import androidx.compose.ui.text.input.getTextBeforeSelection

class TextCustomization {
    companion object {
        fun appendAroundSelected(
            textState: MutableState<TextFieldValue>,
            frontChars: String,
            backChars: String,
        ) {
            val beforeSelection = textState.value.getTextBeforeSelection(Int.MAX_VALUE)
            val selectedText = textState.value.getSelectedText()
            val afterSelection = textState.value.getTextAfterSelection(
                textState.value.text.length - beforeSelection.length - selectedText.length
            )

            if (beforeSelection.text.takeLast(frontChars.length) == frontChars
                && afterSelection.text.take(backChars.length) == backChars) {
                // undo previous text customization
                textState.value = TextFieldValue(
                    "${beforeSelection.substring(0, beforeSelection.length-frontChars.length)}" +
                            "$selectedText" +
                            "${afterSelection.substring(backChars.length)}",
                    TextRange(
                        beforeSelection.length-frontChars.length,
                        beforeSelection.length-frontChars.length+selectedText.length
                    )
                )
            } else {
                // append the chars around the selected text and set selection
                textState.value = TextFieldValue(
                    "$beforeSelection$frontChars$selectedText$backChars$afterSelection",
                    TextRange(
                        beforeSelection.length+frontChars.length,
                        beforeSelection.length+frontChars.length+selectedText.length
                    )
                )
            }
        }

        fun appendAroundSelected(
            textState: MutableState<TextFieldValue>,
            chars: String,
        ) {
            appendAroundSelected(textState, chars, chars)
        }

        fun insertImageTag(
            textState: MutableState<TextFieldValue>,
            imagePath: String,
        ) {
            val beforeSelection = textState.value.getTextBeforeSelection(Int.MAX_VALUE)
            val selectedText = textState.value.getSelectedText()
            val afterSelection = textState.value.getTextAfterSelection(
                textState.value.text.length - beforeSelection.length - selectedText.length
            )
            // Alternatively: ![alt text](maxresdefault.jpg "Title")
            val imgInsert = "<img src=\"file:$imagePath\">"

            textState.value = TextFieldValue(
                "$beforeSelection$selectedText$imgInsert$afterSelection",
                TextRange(beforeSelection.length+imgInsert.length)
            )
        }
    }
}