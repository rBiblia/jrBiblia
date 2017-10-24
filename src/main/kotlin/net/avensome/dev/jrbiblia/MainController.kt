package net.avensome.dev.jrbiblia

import net.avensome.dev.jrbiblia.bibx.BibxCache
import net.avensome.dev.jrbiblia.ext.BibleComparator
import tornadofx.*
import java.nio.file.Files
import java.nio.file.Path

class MainController : Controller() {
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

    fun getTranslations() = BibxCache.values.toSortedSet(BibleComparator)

    fun explore(path: Path) {
        Files.createDirectories(path)
        hostServices.showDocument(path.toUri().toString())
    }
}

class QuantifiableTaskStatus : TaskStatus() {
    val successful = 0.toProperty()
    val failed = 0.toProperty()
}
