package persistence

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.WindowState
import java.io.File

class LocalWindowState {
    val savedReference = ".windowstate"
    val state: WindowState;

    constructor() {
        this.state = loadWindowState();
    }

    private fun loadWindowState(): WindowState {
        val storage: String = File(savedReference).readText(Charsets.UTF_8)
        if (storage != null && storage.isNotEmpty() && storage.split(", ").size == 3) {
            val width: Float = storage.split(", ")[0].toFloat()
            val height: Float = storage.split(", ")[1].toFloat()
            val minimize: Boolean = storage.split(", ")[2].toBoolean()
            return WindowState(size=(DpSize(Dp(width), Dp(height))), isMinimized = minimize);
        }
        return WindowState()
    }

    fun saveState() {
        File(".windowstate").writeText(this.state.size.width.value.toString()
                + ", " + this.state.size.height.value.toString()
                + ", " + this.state.isMinimized)
    }
}