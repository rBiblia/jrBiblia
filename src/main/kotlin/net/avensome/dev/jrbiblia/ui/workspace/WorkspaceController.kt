package net.avensome.dev.jrbiblia.ui.workspace

import javafx.scene.Node
import tornadofx.*

class WorkspaceController : Controller() {
    val model = mutableListOf<Subwindow>().observable()

    init {
        subscribe<CreateWindowEvent> { event ->
            val subwindow = find<Subwindow>(mapOf(Subwindow::initialNodes to listOf(event.node)))
            model.add(subwindow)
        }
        subscribe<CloseWindowEvent> { event -> model.remove(event.window) }
    }
}

class CreateWindowEvent(val node: Node) : FXEvent()

class CloseWindowEvent(val window: Subwindow) : FXEvent()
