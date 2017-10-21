package net.avensome.dev.jrbiblia.resource.bibx

import net.avensome.dev.jbibx.model.Bible
import net.avensome.dev.jbibx.serde.BibxSerde
import java.io.File

open class BibxFile(file: File, val origin: BibxSource.Type) : File(file.toURI()) {
    companion object {
        private val deserializer = BibxSerde()
    }

    constructor(other: BibxFile) : this(other, other.origin)

    open fun load() = LoadedBibxFile(this, deserializer.deserialize(this))
}

class LoadedBibxFile(file: BibxFile, val contents: Bible) : BibxFile(file) {
    override fun load(): LoadedBibxFile = this
}
