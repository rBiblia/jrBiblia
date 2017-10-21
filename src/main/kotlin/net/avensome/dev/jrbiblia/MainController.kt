package net.avensome.dev.jrbiblia

import net.avensome.dev.jbibx.model.Bible
import net.avensome.dev.jrbiblia.ext.BibleComparator
import net.avensome.dev.jrbiblia.resource.bibx.BibxCache
import tornadofx.*

class MainController : Controller() {
    val readerModels = mutableListOf<ReaderFragment>().observable()

    fun reloadTranslations(): MutableCollection<Bible> {
        BibxCache.rebuild()
        return BibxCache.values.toSortedSet(BibleComparator)
    }
}
