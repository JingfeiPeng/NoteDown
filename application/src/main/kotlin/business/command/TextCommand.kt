package business.command

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import business.TextCustomization

class TextCommand(
    private val textState: MutableState<TextFieldValue>,
    private val leftText: String,
    private val rightText: String = leftText,
) : Command {
    override fun runCommand(): Boolean {
        TextCustomization.appendAroundSelected(textState, leftText, rightText)
        return true
    }
}