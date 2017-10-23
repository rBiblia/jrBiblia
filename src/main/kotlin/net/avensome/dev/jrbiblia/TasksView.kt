package net.avensome.dev.jrbiblia

import tornadofx.*

class TasksView : View() {
    private val controller: TasksController by inject()

    override val root = vbox(3.0) {
        bindChildren(controller.tasks) { task ->
            hbox(8.0) {
                progressbar(task.progress)
                label(task.message)
            }
        }
    }
}

