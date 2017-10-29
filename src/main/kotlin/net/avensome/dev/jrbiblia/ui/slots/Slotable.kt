package net.avensome.dev.jrbiblia.ui.slots

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.event.EventHandler
import javafx.scene.control.Button
import net.avensome.dev.jrbiblia.ui.util.Fa
import tornadofx.*
import kotlin.reflect.KClass

abstract class Slotable(controls: KClass<out UIComponent>, contents: KClass<out UIComponent>) : Fragment() {
    val slotableVM: SlotableVM by inject()

    private val masterProperty = SimpleObjectProperty<SlotsController>()
    var master by masterProperty

    final override val root = titledpane(slotableVM.titleProperty, collapsible = false) {
        addClass(Styles.slotable)
        stylesheets += Styles.base64URL.toExternalForm()

        val close = Button(null, Fa((FontAwesomeIcon.TIMES)))
        with(close) {
            onAction = EventHandler { master.slotables.remove(this@Slotable) }
            addClass(Styles.closeButton)
        }
        graphic = close

        borderpane {
            stylesheets += Styles.base64URL.toExternalForm()
            addClass(Styles.slotableContainer)

            top {
                toolbar(find(controls).root)
            }
            center(contents)
        }
    }

    object Styles : Stylesheet() {
        val slotable by cssclass()
        val slotableContainer by cssclass()
        val closeButton by cssclass()

        init {
            s(slotable.child(CssRule.c("title"))) {
                padding = box(0.px)
            }

            s(slotableContainer) {
                padding = box(0.px)
            }

            s(closeButton) {
                borderRadius = MultiValue(arrayOf(box(0.px)))
            }
        }
    }
}

class SlotableVM : ViewModel() {
    val titleProperty: StringProperty = SimpleStringProperty(this, "title", "")
    var title: String by titleProperty
}
