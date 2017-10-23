package net.avensome.dev.jrbiblia

import javafx.scene.control.MenuButton
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import net.avensome.dev.jbibx.model.Bible
import net.avensome.dev.jrbiblia.ext.MenuItem
import tornadofx.*

class MainView : View("JrBiblia") {
    private val controller: MainController by inject()

    private val translationsDropdown = MenuButton(messages["translations"])

    override val root = borderpane {
        top {
            toolbar(translationsDropdown)
        }
        bottom(TasksView::class)
    }

    private val emptyMenuItem: MenuItem = MenuItem(messages["noTranslations"], disabled = true)
    private val reloadMenuItem: MenuItem

    init {
        reloadMenuItem = MenuItem(messages["reloadTranslations"], {
            updateTranslationsDropdown()
        })
        updateTranslationsDropdown()
    }

    private fun updateTranslationsDropdown() {
        with(translationsDropdown) {
            isDisable = true
            items.clear()
        }
        controller.reloadTranslations().completed.onChange {
            val translations = controller.getTranslations()
            with(translationsDropdown) {
                if (translations.isEmpty()) {
                    items.add(emptyMenuItem)
                } else {
                    translations.forEach { items.add(TranslationMenuItem(it)) }
                }
                items.add(SeparatorMenuItem())
                items.add(reloadMenuItem)
                isDisable = false
            }
        }
    }

    class TranslationMenuItem(bible: Bible) : MenuItem(bible.about.name)
}
