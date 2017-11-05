package net.avensome.dev.jrbiblia.ui.workspace.browser

import javafx.scene.control.Label
import tornadofx.*

class Navigator : View() {
    private val vm: BrowserVM by inject()

    override val root = Label()

    init {
        root.text = vm.translation.value.displayName
    }
}
