package net.avensome.dev.jrbiblia.ui.slots.browser

import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.ui.slots.Slotable
import tornadofx.*

class Browser : Slotable(Navigator::class, Reader::class) {
    val translation: Translation by param()

    companion object {
        fun new(translation: Translation): Browser = find(Scope(), mapOf(Browser::translation to translation))
    }

    init {
        root.addClass(Styles.browser)
        root.stylesheets += Styles.base64URL.toExternalForm()
    }

    object Styles : Stylesheet() {
        val browser by cssclass()

        init {
            s(browser) {
                minWidth = 300.px
            }
        }
    }
}
