package net.avensome.dev.jrbiblia.ui.workspace

import tornadofx.*

class WorkspaceView : View() {
    private val controller: WorkspaceController by inject()

    override val root = scrollpane(true, true) {
        hbox {
            bindChildren(controller.windows) { it.root }
        }
    }
}
