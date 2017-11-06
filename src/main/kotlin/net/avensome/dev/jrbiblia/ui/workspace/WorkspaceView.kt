package net.avensome.dev.jrbiblia.ui.workspace

import net.avensome.dev.jrbiblia.ui.workspace.browser.BrowserFragment
import tornadofx.*

class WorkspaceView : View() {
    private val controller: WorkspaceController by inject()

    override val root = scrollpane(true, true) {
        hbox {
            bindChildren(controller.model) { vertical ->
                val nodes = vertical.translations.map { BrowserFragment.new(it).root }
                Subwindow(*nodes.toTypedArray()).root
            }
        }
    }
}
