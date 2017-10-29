package net.avensome.dev.jrbiblia.ui.slots.browser

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import net.avensome.dev.jrbiblia.bibx.Translation
import tornadofx.*

class BrowserState(translation: Translation) {
    val translationProperty = SimpleObjectProperty(this, "translation", translation)
    val bookProperty = SimpleObjectProperty(this, "book", null)
    val chapterProperty = SimpleIntegerProperty(this, "chapter", 1)
    val verseProperty = SimpleIntegerProperty(this, "verse", 1)
}

class BrowserVM(translation: Translation) : ItemViewModel<BrowserState>() {
    init {
        item = BrowserState(translation)
    }

    val translation = bind(BrowserState::translationProperty)
    val book = bind(BrowserState::bookProperty)
    val chapter = bind(BrowserState::chapterProperty)
    val verse = bind(BrowserState::verseProperty)
}
