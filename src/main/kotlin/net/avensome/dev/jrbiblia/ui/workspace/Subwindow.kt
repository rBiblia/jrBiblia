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

class Subwindow : Fragment() {
    override val root = GridPane()
    private val column = ColumnConstraints(300.0, USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
            ALWAYS, LEFT, true)
    private val row = RowConstraints(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE,
            ALWAYS, TOP, true)

    val initialNodes: List<Node> by param()
    private val nodes = mutableListOf<Node>()

    init {
        nodes.addAll(initialNodes)

        root.columnConstraints.setAll(column)
        val rows = List(nodes.size) { row }
        root.rowConstraints.setAll(rows)
        nodes.map { node ->
            val wrapper = AnchorPane(node)
            setAllAnchors(node, 0.0)
            wrapper
        }.forEachIndexed { index, wrapper -> root.add(wrapper, 0, index) }

        subscribe<DetachNodeEvent> { event ->
            nodes.remove(event.node)
            if (nodes.isEmpty()) {
                fire(CloseWindowEvent(this@Subwindow))
            }
        }
    }
}

class DetachNodeEvent(val node: Node) : FXEvent()
