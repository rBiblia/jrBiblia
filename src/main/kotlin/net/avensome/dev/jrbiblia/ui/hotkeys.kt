package net.avensome.dev.jrbiblia.ui

import javafx.event.EventHandler
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyEvent

object HotkeyManager : EventHandler<KeyEvent> {
    private var hotkeys = mutableSetOf<Hotkey>()

    fun add(hotkey: Hotkey) {
        hotkeys.add(hotkey)
    }

    fun clearGroup(group: HotkeyGroup) {
        hotkeys = hotkeys.filter { it.group != group }.toMutableSet()
    }

    override fun handle(event: KeyEvent) {
        val hotkey = hotkeys.firstOrNull { it.matches(event) } ?: return
        hotkey.handler()
    }
}

class Hotkey(val combo: KeyCodeCombination, val group: HotkeyGroup?, val handler: () -> Unit) {
    constructor(keyCode: KeyCode, group: HotkeyGroup?, handler: () -> Unit)
            : this(KeyCodeCombination(keyCode), group, handler)

    override fun hashCode(): Int = combo.hashCode()

    override fun equals(other: Any?): Boolean = other is Hotkey && combo == other.combo

    fun matches(event: KeyEvent) = combo.match(event)
}

enum class HotkeyGroup {
    TRANSLATION
}
