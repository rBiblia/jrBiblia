package net.avensome.dev.jrbiblia.ui.slots

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import tornadofx.*
import kotlin.reflect.KClass

abstract class Slotable(controls: KClass<out UIComponent>, contents: KClass<out UIComponent>) : Fragment() {
    val slotableVM: SlotableVM by inject()

    private val masterProperty = SimpleObjectProperty<SlotsController>()
    var master by masterProperty

    final override val root = titledpane(slotableVM.titleProperty, collapsible = false) {
        stylesheets += Styles.base64URL.toExternalForm()

        SlotableTitle(textProperty(), { master.slotables.remove(this@Slotable) }).attach(this)

        borderpane {
            addClass(Styles.slotableContainer)
            top {
                toolbar(find(controls).root)
            }
            center(contents)
        }
    }

    private object Styles : Stylesheet() {
        val slotableContainer by cssclass()

        init {
            s(slotableContainer) {
                padding = box(0.px)
            }
        }
    }
}

class SlotableVM : ViewModel() {
    val titleProperty: StringProperty = SimpleStringProperty(this, "title", "")
    var title: String by titleProperty
}
