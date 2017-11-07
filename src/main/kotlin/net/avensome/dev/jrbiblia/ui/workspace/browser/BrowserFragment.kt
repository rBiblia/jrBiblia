package net.avensome.dev.jrbiblia.ui.workspace.browser

import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.ui.components.CustomTitledPane
import net.avensome.dev.jrbiblia.ui.workspace.DetachNodeEvent
import tornadofx.*

class BrowserFragment : Fragment() {
    companion object {
        fun new(translation: Translation): BrowserFragment {
            val vm = BrowserVM(translation)
            val scope = Scope(vm)
            return find(scope)
        }
    }

    private val vm: BrowserVM by inject()

    override val root = CustomTitledPane()

    init {
        val navigator = find<Navigator>()
        val reader = find<Reader>()

        root.title = BrowserTitleBar(root.textProperty(), { fire(DetachNodeEvent(root)) })
        root.content = borderpane {
            stylesheets += Styles.base64URL.toExternalForm()
            addClass(Styles.contentPane)
            top {
                toolbar(navigator.root)
            }
            center = reader.root
        }

        root.text = vm.translation.get().displayName
    }

    private object Styles : Stylesheet() {
        val contentPane by cssclass()

        init {
            s(contentPane) {
                padding = box(0.px)
            }
        }
    }
}
