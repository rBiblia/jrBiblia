package net.avensome.dev.jrbiblia

import net.avensome.dev.jrbiblia.ui.MainView
import net.avensome.dev.jrbiblia.ui.Styles
import tornadofx.*

class JrBibliaApp : App(MainView::class, Styles::class) {
    override val primaryView = MainView::class

    init {
        importStylesheet(Style::class)
    }

    class Style : Stylesheet() {
        init {
            root {
                prefWidth = 800.px
                prefHeight = 560.px
            }
        }
    }
}
