package net.avensome.dev.jrbiblia.ui.translations

import javafx.scene.control.TitledPane
import javafx.scene.layout.Pane
import net.avensome.dev.jrbiblia.bibx.BibxCache
import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.ui.tasks.TasksController
import net.avensome.dev.jrbiblia.ui.workspace.Window
import net.avensome.dev.jrbiblia.ui.workspace.WorkspaceController
import net.avensome.dev.jrbiblia.ui.workspace.browser.BrowserPane
import tornadofx.*
import java.nio.file.Files
import java.nio.file.Path

class TranslationsController : Controller() {
    private val tasksController: TasksController by inject()
    private val workspaceController: WorkspaceController by inject()

    fun reloadTranslations(): TaskStatus {
        val task = QuantifiableTaskStatus()
        tasksController.tasks.add(task)
        runAsync(task) {
            task.failed.onChange { count -> updateMessage(messages["loadingTranslationsWithErrors"].format(count)) }
            updateMessage(messages["loadingTranslations"])
            BibxCache.rebuild().subscribe { progress ->
                task.successful.set(progress.successful)
                task.failed.set(progress.failed)
                updateProgress(progress.processed.toLong(), progress.total.toLong())
            }
        }
        return task
    }

    fun getLoadedTranslations() = BibxCache

    fun explore(path: Path) {
        Files.createDirectories(path)
        hostServices.showDocument(path.toUri().toString())
    }

    fun openTranslation(translation: Translation) {
        val browser = BrowserPane.new(translation)
        val window = if (workspaceController.windows.count() % 2 == 0)
            Window(browser.root)
        else
            Window(browser.root, TitledPane("bottom", Pane()))
        workspaceController.windows.add(window)
    }
}

class QuantifiableTaskStatus : TaskStatus() {
    val successful = 0.toProperty()
    val failed = 0.toProperty()
}
