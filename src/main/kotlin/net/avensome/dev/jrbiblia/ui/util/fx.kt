package net.avensome.dev.jrbiblia.ui.util

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.MenuItem
import javafx.scene.layout.AnchorPane

fun MenuItem(text: String, handler: (ActionEvent) -> Unit = {}, disabled: Boolean = false): MenuItem {
    val item = MenuItem(text)
    item.onAction = EventHandler { handler(it) }
    item.isDisable = disabled
    return item
}

fun setAllAnchors(child: Node, value: Double) {
    AnchorPane.setTopAnchor(child, value)
    AnchorPane.setRightAnchor(child, value)
    AnchorPane.setBottomAnchor(child, value)
    AnchorPane.setLeftAnchor(child, value)
}
