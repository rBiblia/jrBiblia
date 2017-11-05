package net.avensome.dev.jrbiblia.ui

import javafx.scene.input.KeyEvent
import net.avensome.dev.jrbiblia.ui.tasks.TasksView
import net.avensome.dev.jrbiblia.ui.translations.TranslationsView
import net.avensome.dev.jrbiblia.ui.workspace.WorkspaceView
import tornadofx.*

class MainView : View("JrBiblia") {
    override val root = borderpane {
        top(TranslationsView::class)
        center(WorkspaceView::class)
        bottom(TasksView::class)
    }

    init {
        root.addEventFilter(KeyEvent.KEY_PRESSED, HotkeyManager)
    }
}
