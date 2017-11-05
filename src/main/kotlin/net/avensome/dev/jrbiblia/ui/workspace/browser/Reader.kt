package net.avensome.dev.jrbiblia.ui.workspace.browser

import javafx.scene.control.Button
import tornadofx.*

class Reader : View() {
    private val vm: BrowserVM by inject()

    override val root = Button("contents")
}
