package net.avensome.dev.jrbiblia.ui.slots

import tornadofx.*

class SlotsController : Controller() {
    val slotables = mutableListOf<Slotable>().observable()

    init {
        slotables.onChange { change ->
            change.next()
            change.removed.forEach { it.master = null }
            change.addedSubList.forEach { it.master = this@SlotsController }
        }
    }
}
