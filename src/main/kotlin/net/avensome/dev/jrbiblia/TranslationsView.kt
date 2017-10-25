package net.avensome.dev.jrbiblia

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.scene.control.Button
import javafx.scene.control.MenuButton
import javafx.scene.control.MenuItem
import javafx.scene.control.Tooltip
import javafx.scene.layout.Region
import net.avensome.dev.jrbiblia.bibx.BibxProvider
import net.avensome.dev.jrbiblia.bibx.Translation
import net.avensome.dev.jrbiblia.ext.MenuItem
import net.avensome.dev.jrbiblia.ext.Rectangle
import net.avensome.dev.jrbiblia.ext.nonEmptyShortName
import net.avensome.dev.jrbiblia.ext.orIfBlank
import net.avensome.dev.jrbiblia.util.Fa
import org.apache.commons.lang3.SystemUtils
import tornadofx.*
import java.nio.file.Path

class TranslationsView : View() {
    private val controller: TranslationsController by inject()

    private val translationButtons = mutableListOf<TranslationButton>().observable()
    private val moreMenuItems = mutableListOf<MenuItem>().observable()
    private val moreMenuButton = MenuButton(null, Fa(FontAwesomeIcon.BARS))

    override val root = toolbar {
        borderpane {
            val width = this@toolbar.widthProperty().minus(this@toolbar.paddingHorizontalProperty.times(2))
            prefWidthProperty().bind(width)

            center {
                hbox(5) {
                    minWidth = 0.0
                    val clipRectangle = Rectangle(this@center.widthProperty(), heightProperty())
                    clipProperty().bind(clipRectangle)
                    prefWidth = Region.USE_COMPUTED_SIZE
                    bindChildren(translationButtons) { it }
                }
            }
            right {
                add(moreMenuButton)
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
        controller.reloadTranslations().completed.onChange {
            val translations = controller.getLoadedTranslations().toSortedSet()
            translations.forEach { translationButtons.add(TranslationButton(it)) }
        }
    }

    private fun exploreMenuItem(text: String, path: Path) = MenuItem(text, { controller.explore(path) })

    class TranslationButton(translation: Translation) : Button(translation.contents.about.nonEmptyShortName) {
        init {
            minWidth = Button.USE_PREF_SIZE
            tooltip = Tooltip(translation.contents.about.name.orIfBlank(translation.nameWithoutExtension))
        }
    }
}
