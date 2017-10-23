package net.avensome.dev.jrbiblia

import net.avensome.dev.jrbiblia.bibx.BibxCache
import net.avensome.dev.jrbiblia.ext.BibleComparator
import tornadofx.*

class MainController : Controller() {
    private val tasksController: TasksController by inject()

    fun reloadTranslations(): TaskStatus {
        val task = TaskStatus()
        tasksController.tasks.add(task)
        runAsync(task) {
            updateMessage(messages["loadingTranslations"])
            BibxCache.rebuild().subscribe {
                updateProgress(it.loaded.toLong(), it.total.toLong())
            }
        }
        return task
    }

    fun getTranslations() = BibxCache.values.toSortedSet(BibleComparator)
}
