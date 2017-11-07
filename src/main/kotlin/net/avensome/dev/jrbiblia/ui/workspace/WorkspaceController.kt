package net.avensome.dev.jrbiblia.ui.workspace

import javafx.scene.Node
import tornadofx.*

class WorkspaceController : Controller() {
    val verticals = mutableListOf<VerticalFragment>().observable()

    init {
        subscribe<CreateVerticalEvent> { event ->
            val vertical = find<VerticalFragment>(mapOf(VerticalFragment::initialWindows to listOf(event.node)))
            verticals.add(vertical)
        }
        subscribe<CloseVerticalEvent> { event -> verticals.remove(event.vertical) }
    }
}

class CreateVerticalEvent(val node: Node) : FXEvent()

class CloseVerticalEvent(val vertical: VerticalFragment) : FXEvent()
