package net.avensome.dev.jrbiblia.ui.slots.browser

import javafx.scene.control.Button
import tornadofx.*

class Reader : View() {
    private val vm: BrowserVM by inject()

    override val root = Button("contents")
}
