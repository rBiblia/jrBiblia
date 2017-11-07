package net.avensome.dev.jrbiblia.ui.workspace.browser

import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.ui.workspace.SubwindowFragment
import tornadofx.*

class BrowserFragment : SubwindowFragment() {
    companion object {
        fun new(translation: Translation): BrowserFragment {
            val vm = BrowserVM(translation)
            val scope = Scope(vm)
            return find(scope)
        }
    }

    private val vm: BrowserVM by inject()

    init {
        val navigator = find<Navigator>()
        val reader = find<Reader>()

        root.content = borderpane {
            stylesheets += SubwindowStyles.base64URL.toExternalForm()
            addClass(SubwindowStyles.contentPane)
            top {
                toolbar(navigator.root)
            }
            center = reader.root
        }

        root.text = vm.translation.get().displayName
    }
}
