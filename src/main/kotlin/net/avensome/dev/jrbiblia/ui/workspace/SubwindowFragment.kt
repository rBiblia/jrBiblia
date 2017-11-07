package net.avensome.dev.jrbiblia.ui.workspace

import net.avensome.dev.jrbiblia.ui.components.CustomTitledPane
import tornadofx.*

abstract class SubwindowFragment : Fragment() {
    final override val root = CustomTitledPane()

    init {
        root.title = SubwindowTitleBar(root.textProperty(), { fire(CloseWindowEvent(root)) })
    }

    protected object SubwindowStyles : Stylesheet() {
        val contentPane by cssclass()

        init {
            s(contentPane) {
                padding = box(0.px)
            }
        }
    }
}
