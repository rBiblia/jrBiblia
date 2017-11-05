package net.avensome.dev.jrbiblia.ui.components

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.beans.property.ReadOnlyStringProperty
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import net.avensome.dev.jrbiblia.ui.util.Fa
import tornadofx.*

class CustomTitle(text: ReadOnlyStringProperty, closeHandler: () -> Unit) : BorderPane() {
    private val close = Button(null, Fa(FontAwesomeIcon.TIMES))
    private val label = Label()

    init {
        with(close) {
            addClass(Styles.closeButton)
            onAction = EventHandler { closeHandler() }
        }
        label.textProperty().bind(text)

        center = label
        right = close
    }

    private object Styles : Stylesheet() {
        val closeButton by cssclass()

        init {
            s(closeButton) {
                borderRadius = MultiValue(arrayOf(box(0.px)))
            }
        }
    }
}
