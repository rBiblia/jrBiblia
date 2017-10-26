package net.avensome.dev.jrbiblia.ui

import net.avensome.dev.jrbiblia.bibx.BibxCache
import net.avensome.dev.jrbiblia.bibx.Translation
import tornadofx.*
import java.nio.file.Files
import java.nio.file.Path

class TranslationsController : Controller() {
    private val tasksController: TasksController by inject()

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
        println(translation.nameWithoutExtension)
    }
}

class QuantifiableTaskStatus : TaskStatus() {
    val successful = 0.toProperty()
    val failed = 0.toProperty()
}
