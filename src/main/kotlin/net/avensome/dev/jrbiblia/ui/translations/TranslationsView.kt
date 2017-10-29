package net.avensome.dev.jrbiblia.ui.translations

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import net.avensome.dev.jrbiblia.bibx.BibxProvider
import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.bibx.withFallback
import net.avensome.dev.jrbiblia.ui.Hotkey
import net.avensome.dev.jrbiblia.ui.HotkeyGroup
import net.avensome.dev.jrbiblia.ui.HotkeyManager
import net.avensome.dev.jrbiblia.ui.util.Fa
import net.avensome.dev.jrbiblia.ui.util.MenuItem
import net.avensome.dev.jrbiblia.util.orIfBlank
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
            button.graphic = HotkeyLabel(keyCode)
        }
    }

    private fun exploreMenuItem(text: String, path: Path) = MenuItem(text, { controller.explore(path) })

    class TranslationButton(val translation: Translation, openHandler: (Translation) -> Unit)
        : Button(translation.contents.about.withFallback.shortName) {
        override fun getUserAgentStylesheet(): String = Styles().base64URL.toExternalForm()

        init {
            minWidth = Button.USE_PREF_SIZE
            tooltip = Tooltip(translation.contents.about.name.orIfBlank(translation.nameWithoutExtension))
            onAction = EventHandler { openHandler(translation) }

            graphicProperty().onChange { updateGraphicClass() }
        }

        private fun updateGraphicClass() {
            if (graphic != null) {
                addClass(Styles.withGraphic)
            } else {
                removeClass(Styles.withGraphic)
            }
        }

        class Styles : Stylesheet() {
            companion object {
                val withGraphic by cssclass()
            }

            init {
                s(withGraphic) {
                    padding = box(4.px, 8.px, 4.px, 4.px)
                }
            }
        }
    }

    class HotkeyLabel(keyCode: KeyCode) : Label(keyCode.name) {
        override fun getUserAgentStylesheet(): String = Styles().base64URL.toExternalForm()

        init {
            addClass(Styles.hotkeyLabel)
        }

        class Styles : Stylesheet() {
            companion object {
                val hotkeyLabel by cssclass()
            }

            init {
                s(hotkeyLabel) {
                    fontSize = 10.px
                    padding = box(0.px, 2.px)
                    backgroundColor += Color(1.0, 1.0, 1.0, 0.5)
                    borderColor += box(Color(0.0, 0.0, 0.0, 0.5))
                    borderRadius += box(2.px)
                    borderInsets = MultiValue(arrayOf(box(0.px)))
                }
            }
        }
    }
}
