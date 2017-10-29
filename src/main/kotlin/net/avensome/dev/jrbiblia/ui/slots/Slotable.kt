package net.avensome.dev.jrbiblia.ui.slots

import tornadofx.*
import kotlin.reflect.KClass

abstract class Slotable(controls: KClass<out UIComponent>, contents: KClass<out UIComponent>) : Fragment() {
    override val root = borderpane {
        top {
            toolbar(find(controls).root)
        }
        center(contents)
    }
}
