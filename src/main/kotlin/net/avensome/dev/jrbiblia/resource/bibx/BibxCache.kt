package net.avensome.dev.jrbiblia.resource.bibx

import net.avensome.dev.jbibx.model.Bible

object BibxCache : HashMap<LoadedBibxFile, Bible>() {
    fun rebuild() {
        val loadedFiles = BibxProvider.getBibxFiles().map { it.load() }
        synchronized(this) {
            clear()
            loadedFiles.forEach { put(it, it.contents) }
        }
    }
}
