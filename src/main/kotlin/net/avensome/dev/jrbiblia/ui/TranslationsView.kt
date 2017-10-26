package net.avensome.dev.jrbiblia.ui

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.MenuButton
import javafx.scene.control.MenuItem
import javafx.scene.control.Tooltip
import javafx.scene.input.KeyCode
import net.avensome.dev.jrbiblia.bibx.BibxProvider
import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.ext.MenuItem
import net.avensome.dev.jrbiblia.ext.nonEmptyShortName
import net.avensome.dev.jrbiblia.ext.orIfBlank
import net.avensome.dev.jrbiblia.util.Fa
import org.apache.commons.lang3.SystemUtils
import tornadofx.*
import java.nio.file.Path

class TranslationsView : View() {
    companion object {
        private val fKeyCodes = arrayOf(KeyCode.F1, KeyCode.F2, KeyCode.F3, KeyCode.F4, KeyCode.F5, KeyCode.F6,
                KeyCode.F7, KeyCode.F8, KeyCode.F9, KeyCode.F10, KeyCode.F11, KeyCode.F12)
    }

    private val controller: TranslationsController by inject()

    private val translationButtons = mutableListOf<TranslationButton>().observable()
    private val moreMenuItems = mutableListOf<MenuItem>().observable()
    private val moreMenuButton = MenuButton(null, Fa(FontAwesomeIcon.BARS))

    override val root = borderpane {
        center {
            toolbar {
                prefHeightProperty().bind(this@borderpane.heightProperty())
                bindChildren(translationButtons) { it }
            }
        }
        right {
            toolbar {
                prefHeightProperty().bind(this@borderpane.heightProperty())
                this += moreMenuButton
            }
        }
    }

    init {
        moreMenuItems.setAll(mutableListOf(
                MenuItem(messages["reloadTranslations"], { updateItems() }),
                exploreMenuItem(messages["exploreLocalTranslations"], BibxProvider.getLocalSource())
        ))
        if (SystemUtils.IS_OS_WINDOWS) {
            moreMenuItems.add(
                    exploreMenuItem(messages["exploreExternalTranslations"], BibxProvider.getExternalSource())
            )
        }

        moreMenuButton.items.setAll(moreMenuItems)
        moreMenuButton.prefHeight = 25.0

        updateItems()
    }

    private fun updateItems() {
        translationButtons.clear()
        controller.reloadTranslations().completed.onChange { onTranslationsLoaded() }
    }

    private fun onTranslationsLoaded() {
        val translations = controller.getLoadedTranslations().toSortedSet()
        translations.forEach { translationButtons.add(TranslationButton(it, controller::openTranslation)) }
        HotkeyManager.clearGroup(HotkeyGroup.TRANSLATION)
        for ((button, keyCode) in translationButtons.zip(fKeyCodes)) {
            HotkeyManager.add(Hotkey(keyCode, HotkeyGroup.TRANSLATION) {
                controller.openTranslation(button.translation)
            })
        }
    }

    private fun exploreMenuItem(text: String, path: Path) = MenuItem(text, { controller.explore(path) })

    class TranslationButton(val translation: Translation, openHandler: (Translation) -> Unit) : Button(translation.contents.about.nonEmptyShortName) {
        init {
            minWidth = Button.USE_PREF_SIZE
            tooltip = Tooltip(translation.contents.about.name.orIfBlank(translation.nameWithoutExtension))
            onAction = EventHandler { openHandler(translation) }
        }
    }
}
