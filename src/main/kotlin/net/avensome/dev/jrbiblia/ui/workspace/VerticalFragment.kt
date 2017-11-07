package net.avensome.dev.jrbiblia.ui.workspace

import javafx.geometry.HPos.LEFT
import javafx.geometry.VPos.TOP
import javafx.scene.Node
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority.ALWAYS
import javafx.scene.layout.Region.USE_COMPUTED_SIZE
import javafx.scene.layout.RowConstraints
import net.avensome.dev.jrbiblia.ui.util.setAllAnchors
import tornadofx.*

class VerticalFragment : Fragment() {
    override val root = GridPane()
    private val column = ColumnConstraints(300.0, USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
            ALWAYS, LEFT, true)
    private val row = RowConstraints(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE,
            ALWAYS, TOP, true)

    val initialWindows: List<Node> by param()
    private val windows = mutableListOf<Node>()

    init {
        windows.addAll(initialWindows)

        root.columnConstraints.setAll(column)
        val rows = List(windows.size) { row }
        root.rowConstraints.setAll(rows)
        windows.map { window ->
            val wrapper = AnchorPane(window)
            setAllAnchors(window, 0.0)
            wrapper
        }.forEachIndexed { index, wrapper -> root.add(wrapper, 0, index) }

        subscribe<CloseWindowEvent> { event ->
            windows.remove(event.window)
            if (windows.isEmpty()) {
                fire(CloseVerticalEvent(this@VerticalFragment))
            }
        }
    }
}

class CloseWindowEvent(val window: Node) : FXEvent()
