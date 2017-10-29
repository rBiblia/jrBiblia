package net.avensome.dev.jrbiblia.ui.slots

import tornadofx.*

class SlotsView : View() {
    private val controller: SlotsController by inject()

    override val root = scrollpane(true, true) {
        hbox {
            bindChildren(controller.slotables) { it.root }
        }
    }
}
