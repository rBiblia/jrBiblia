package net.avensome.dev.jrbiblia

import tornadofx.*

class JrBibliaApp : App() {
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
