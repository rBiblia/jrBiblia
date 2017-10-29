package net.avensome.dev.jrbiblia.ui.slots

import javafx.scene.Node
import tornadofx.*

abstract class Slotable : Fragment() {
    val toolbarNodes: Array<Node> by param()
    val contents: Node by param()

    override val root = borderpane {
        top {
            toolbar(*toolbarNodes)
        }
        center {
            this += contents
        }
    }
}
