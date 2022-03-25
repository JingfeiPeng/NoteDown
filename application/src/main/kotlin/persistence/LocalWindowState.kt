package persistence

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import java.io.File

class LocalWindowState {
    private val savedReferenceFolder = System.getProperty("user.home") + "/NotesTaker"
    private val savedReference = ".windowstate"

    val state: WindowState;

    constructor() {
        this.state = loadWindowState();
    }

    private fun isWindowStateStored(): Boolean {
        return File(this.savedReferenceFolder, this.savedReference).exists()
    }

    private fun loadWindowState(): WindowState {
        if (this.isWindowStateStored()) {
            val storage: String = File(this.savedReferenceFolder, this.savedReference).readText(Charsets.UTF_8)
            if (storage != null && storage.isNotEmpty() && storage.split(", ").size == 5) {
                val width: Float = storage.split(", ")[0].toFloat()
                val height: Float = storage.split(", ")[1].toFloat()
                val xpos: Float = storage.split(", ")[2].toFloat()
                val ypos: Float = storage.split(", ")[3].toFloat()
                val minimize: Boolean = storage.split(", ")[4].toBoolean()
                return WindowState(
                    size = (DpSize(Dp(width), Dp(height))),
                    position = WindowPosition(Dp(xpos), Dp(ypos)),
                    isMinimized = minimize
                );
            }
        }
        return WindowState()
    }

    fun saveState() {
        File(this.savedReferenceFolder, this.savedReference).writeText(
            this.state.size.width.value.toString()
                + ", " + this.state.size.height.value.toString()
                + ", " + this.state.position.x.value.toString()
                + ", " + this.state.position.y.value.toString()
                + ", " + this.state.isMinimized)
    }
}