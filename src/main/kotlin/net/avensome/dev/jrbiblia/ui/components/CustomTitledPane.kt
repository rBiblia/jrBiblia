package net.avensome.dev.jrbiblia.ui.components

import javafx.scene.Node
import javafx.scene.control.ContentDisplay
import javafx.scene.control.TitledPane
import javafx.scene.layout.AnchorPane
import net.avensome.dev.jrbiblia.ui.util.setAllAnchors
import tornadofx.*

open class CustomTitledPane : TitledPane() {
    private val titlePane = AnchorPane()

    var title: Node?
        get() = titlePane.children[0] ?: null
        set(node) {
            if (node == null) {
                titlePane.children.clear()
            } else {
                titlePane.children.setAll(node)
                setAllAnchors(node, 0.0)
            }
        }

    init {
        stylesheets += Styles.base64URL.toExternalForm()
        addClass(Styles.customTitledPane)
        graphic = titlePane
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
        isCollapsible = false
        titlePane.prefWidthProperty().bind(widthProperty())
    }

    private object Styles : Stylesheet() {
        val customTitledPane by cssclass()

        init {
            s(customTitledPane.child(CssRule.c("title"))) {
                padding = box(0.px)
            }
        }
    }
}
