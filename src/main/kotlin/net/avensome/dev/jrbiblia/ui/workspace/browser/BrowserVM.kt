package net.avensome.dev.jrbiblia.ui.workspace.browser

import javafx.beans.property.ReadOnlyObjectProperty
import net.avensome.dev.jbibx.model.BookID
import net.avensome.dev.jrbiblia.bibx.Translation
import tornadofx.*

class BrowserState(val translation: Translation) {
    var book: BookID = translation.contents.books.first().id
    var chapter: Int = 1
    var verse: Int = 1
}

class BrowserVM(translation: Translation) : ItemViewModel<BrowserState>(BrowserState(translation)) {
    val translation: ReadOnlyObjectProperty<Translation> = bind { item.translation.toProperty() }
    val book = bind { item.book.toProperty() }
    val chapter = bind { item.chapter.toProperty() }
    val verse = bind { item.verse.toProperty() }
}
