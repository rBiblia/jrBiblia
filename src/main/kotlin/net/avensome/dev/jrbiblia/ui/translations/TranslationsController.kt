package net.avensome.dev.jrbiblia.ui.translations

import net.avensome.dev.jrbiblia.bibx.BibxCache
import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.ui.tasks.TasksController
import net.avensome.dev.jrbiblia.ui.workspace.Vertical
import net.avensome.dev.jrbiblia.ui.workspace.WorkspaceController
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
        val window = if (workspaceController.model.count() % 2 == 0)
            Vertical(translation)
        else
            Vertical(translation, translation)
        workspaceController.model.add(window)
    }
}

class QuantifiableTaskStatus : TaskStatus() {
    val successful = 0.toProperty()
    val failed = 0.toProperty()
}
