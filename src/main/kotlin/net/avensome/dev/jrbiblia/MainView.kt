package net.avensome.dev.jrbiblia

import tornadofx.*

class MainView : View("JrBiblia") {
    override val root = borderpane {
        top {
            toolbar {
                label("Hello world")
            }
        }
    }
}
