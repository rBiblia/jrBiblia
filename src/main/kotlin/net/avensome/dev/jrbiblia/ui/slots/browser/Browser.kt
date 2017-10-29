package net.avensome.dev.jrbiblia.ui.slots.browser

import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.ui.slots.Slotable
import tornadofx.*

class Browser : Slotable(Navigator::class, Reader::class) {
    companion object {
        fun new(translation: Translation): Browser {
            val vm = BrowserVM(translation)
            val scope = Scope(vm)
            return find(scope)
        }
    }

    private val vm: BrowserVM by inject()

    init {
        root.addClass(Styles.browser)
        root.stylesheets += Styles.base64URL.toExternalForm()
        slotableVM.title = vm.translation.get().displayName
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
