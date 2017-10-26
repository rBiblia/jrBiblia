package net.avensome.dev.jrbiblia

import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import net.avensome.dev.jrbiblia.ui.MainView
import tornadofx.*

class JrBibliaApp : App(MainView::class, Style::class) {
    companion object {
        val transparent = MultiValue(arrayOf<Paint>(Color.TRANSPARENT))
    }

    override val primaryView = MainView::class

    class Style : Stylesheet() {
        init {
            root {
                prefWidth = 800.px
                prefHeight = 560.px
            }
            scrollPane {
                backgroundColor = transparent

                s(viewport) {
                    backgroundColor = transparent
                }
            }
        }
    }
}
