package net.avensome.dev.jrbiblia.bibx

import net.avensome.dev.jbibx.model.Bible
import net.avensome.dev.jbibx.serde.BibxSerde
import java.io.File

object BibxCache : HashMap<LoadedBibxFile, Bible>() {
    fun rebuild() {
        val loadedFiles = BibxProvider.getBibxFiles().map { it.load() }
        synchronized(this) {
            clear()
            loadedFiles.forEach { put(it, it.contents) }
        }
    }
}

class LoadedBibxFile(file: File, val contents: Bible) : File(file.toURI())

object Deserializer : BibxSerde()

fun File.load(): LoadedBibxFile = LoadedBibxFile(this, Deserializer.deserialize(this))
