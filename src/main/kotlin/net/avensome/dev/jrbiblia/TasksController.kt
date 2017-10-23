package net.avensome.dev.jrbiblia

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class TasksController : Controller() {
    val tasks: ObservableList<TaskStatus> = FXCollections.observableArrayList()

    init {
        tasks.onChange { change ->
            change.next()
            change.addedSubList.forEach { added ->
                added.completed.onChange {
                    tasks.remove(added)
                }
            }
        }
    }
}
