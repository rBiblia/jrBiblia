package net.avensome.dev.jrbiblia.ui

import javafx.scene.input.KeyEvent
import net.avensome.dev.jrbiblia.ui.tasks.TasksView
import net.avensome.dev.jrbiblia.ui.translations.TranslationsView
import tornadofx.*

class MainView : View("JrBiblia") {
    override val root = borderpane {
        top(TranslationsView::class)
        bottom(TasksView::class)
    }

    init {
        root.addEventFilter(KeyEvent.KEY_PRESSED, HotkeyManager)
    }
}
