package persistence

import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import java.io.File

class LocalWindowState {
    private val savedReferenceFolder = System.getProperty("user.home") + "/NotesTaker"
    private val savedReference = ".windowstate"

    val state: WindowState;
    var colorTheme: Int = 0;

    constructor() {
        this.state = loadWindowState();
    }

    private fun isWindowStateStored(): Boolean {
        return File(this.savedReferenceFolder, this.savedReference).exists()
    }

    private fun loadWindowState(): WindowState {
        if (this.isWindowStateStored()) {
            val storage: String = File(this.savedReferenceFolder, this.savedReference).readText(Charsets.UTF_8)
            if (storage.isNotEmpty() && storage.split(", ").size == 6) {
                val width: Float = storage.split(", ")[0].toFloat()
                val height: Float = storage.split(", ")[1].toFloat()
                val xpos: Float = storage.split(", ")[2].toFloat()
                val ypos: Float = storage.split(", ")[3].toFloat()
                val minimize: Boolean = storage.split(", ")[4].toBoolean()
                this.colorTheme = storage.split(", ")[5].toInt()
                return WindowState(
                    size = (DpSize(Dp(width), Dp(height))),
                    position = WindowPosition(Dp(xpos), Dp(ypos)),
                    isMinimized = minimize
                );
            }
        }
        return WindowState()
    }

    fun saveState(colorSelection: MutableState<Int>) {
        File(this.savedReferenceFolder, this.savedReference).writeText(
            this.state.size.width.value.toString()
                    + ", " + this.state.size.height.value.toString()
                    + ", " + this.state.position.x.value.toString()
                    + ", " + this.state.position.y.value.toString()
                    + ", " + this.state.isMinimized
                    + ", " + colorSelection.value.toString()
        )
    }
}