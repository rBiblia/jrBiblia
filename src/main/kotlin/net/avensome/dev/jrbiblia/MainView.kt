package net.avensome.dev.jrbiblia

import javafx.scene.control.MenuButton
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import net.avensome.dev.jbibx.model.Bible
import net.avensome.dev.jrbiblia.bibx.BibxProvider
import net.avensome.dev.jrbiblia.ext.MenuItem
import org.apache.commons.lang3.SystemUtils
import tornadofx.*
import java.nio.file.Path

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
    private val translationsMenuFixedItems: List<MenuItem>

    init {
        translationsMenuFixedItems = mutableListOf(
                SeparatorMenuItem(),
                MenuItem(messages["reloadTranslations"], { updateTranslationsDropdown() }),
                exploreTranslationsMenuItem(messages["exploreLocalTranslations"], BibxProvider.getLocalSource())
        )
        if (SystemUtils.IS_OS_WINDOWS) {
            val item = exploreTranslationsMenuItem(messages["exploreExternalTranslations"], BibxProvider.getExternalSource())
            translationsMenuFixedItems.add(item)
        }
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
                items.addAll(translationsMenuFixedItems)
                isDisable = false
            }
        }
    }

    private fun exploreTranslationsMenuItem(text: String, path: Path) = MenuItem(text, { controller.explore(path) })

    class TranslationMenuItem(bible: Bible) : MenuItem(bible.about.name)
}
