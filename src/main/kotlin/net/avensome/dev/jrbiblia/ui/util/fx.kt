package net.avensome.dev.jrbiblia.ui.util

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.MenuItem

fun MenuItem(text: String, handler: (ActionEvent) -> Unit = {}, disabled: Boolean = false): MenuItem {
    val item = MenuItem(text)
    item.onAction = EventHandler { handler(it) }
    item.isDisable = disabled
    return item
}
