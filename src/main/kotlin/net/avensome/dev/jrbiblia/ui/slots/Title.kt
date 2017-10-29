package net.avensome.dev.jrbiblia.ui.slots

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.beans.property.ReadOnlyStringProperty
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.control.TitledPane
import javafx.scene.layout.BorderPane
import net.avensome.dev.jrbiblia.ui.util.Fa
import tornadofx.*

class SlotableTitle(text: ReadOnlyStringProperty, closeHandler: () -> Unit) : BorderPane() {
    private val close = Button(null, Fa((FontAwesomeIcon.TIMES)))
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

    fun attach(titledPane: TitledPane) {
        prefWidthProperty().bind(titledPane.widthProperty())
        with(titledPane) {
            stylesheets += Styles.base64URL.toExternalForm()
            addClass(Styles.slotable)
            graphic = this@SlotableTitle
            contentDisplay = ContentDisplay.GRAPHIC_ONLY
        }
    }

    private object Styles : Stylesheet() {
        val slotable by cssclass()
        val closeButton by cssclass()

        init {
            s(slotable.child(CssRule.c("title"))) {
                padding = box(0.px)
            }

            s(Styles.closeButton) {
                borderRadius = MultiValue(arrayOf(box(0.px)))
            }
        }
    }
}
