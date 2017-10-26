package net.avensome.dev.jrbiblia.ui

import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val viewport by cssclass()
        val transparent = MultiValue(arrayOf<Paint>(Color.TRANSPARENT))
    }

    init {
        scrollPane {
            backgroundColor = transparent

            s(viewport) {
                backgroundColor = transparent
            }
        }
    }
}
