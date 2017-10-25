package net.avensome.dev.jrbiblia

import javafx.scene.control.*
import net.avensome.dev.jrbiblia.bibx.BibxProvider
import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.ext.MenuItem
import net.avensome.dev.jrbiblia.ext.nonEmptyShortName
import net.avensome.dev.jrbiblia.ext.orIfBlank
import org.apache.commons.lang3.SystemUtils
import tornadofx.*
import java.nio.file.Path

class TranslationsView : View() {
    private val controller: TranslationsController by inject()

    private val translationButtons = mutableListOf<TranslationButton>().observable()
    private val translationsDropdown = MenuButton(messages["translations"])

    override val root = toolbar(translationsDropdown) {
        bindChildren(translationButtons, { it })
    }

    private val translationsMenuFixedItems: List<MenuItem>

    init {
        translationsMenuFixedItems = mutableListOf(  // TODO add to UI
                SeparatorMenuItem(),
                MenuItem(messages["reloadTranslations"], { updateItems() }),
                exploreTranslationsMenuItem(messages["exploreLocalTranslations"], BibxProvider.getLocalSource())
        )
        if (SystemUtils.IS_OS_WINDOWS) {
            val item = exploreTranslationsMenuItem(messages["exploreExternalTranslations"], BibxProvider.getExternalSource())
            translationsMenuFixedItems.add(item)
        }
        updateItems()
    }

    private fun updateItems() {
        translationButtons.clear()
        controller.reloadTranslations().completed.onChange {
            val translations = controller.getLoadedTranslations().toSortedSet()
            translations.forEach { translationButtons.add(TranslationButton(it)) }
        }
    }

    private fun exploreTranslationsMenuItem(text: String, path: Path) = MenuItem(text, { controller.explore(path) })

    class TranslationButton(translation: Translation) : Button(translation.contents.about.nonEmptyShortName) {
        init {
            tooltip = Tooltip(translation.contents.about.name.orIfBlank(translation.nameWithoutExtension))
        }
    }
}
