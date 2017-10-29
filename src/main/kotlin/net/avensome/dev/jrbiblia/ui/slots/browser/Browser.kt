package net.avensome.dev.jrbiblia.ui.slots.browser

import javafx.scene.Node
import net.avensome.dev.jrbiblia.ui.slots.Slotable
import tornadofx.*

class Browser : Slotable() {
    companion object {
        fun new(toolbarNodes: Array<Node>, contents: Node): Browser = find(Scope(), mapOf(
                Slotable::toolbarNodes to toolbarNodes,
                Slotable::contents to contents
        ))
    }

    init {
        root.addClass(Styles.browser)
        root.stylesheets += Styles.base64URL.toExternalForm()
    }

    object Styles : Stylesheet() {
        val browser by cssclass()

        init {
            s(browser) {
                minWidth = 300.px
            }
        }
    }
}
