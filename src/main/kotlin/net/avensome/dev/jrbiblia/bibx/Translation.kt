package net.avensome.dev.jrbiblia.bibx

import net.avensome.dev.jbibx.model.Bible
import net.avensome.dev.jrbiblia.util.orIfBlank
import java.io.File

class Translation(file: File, val contents: Bible) : File(file.toURI()) {
    val displayName = contents.about.withFallback.name.orIfBlank(file.nameWithoutExtension)

    override fun compareTo(other: File?): Int =
            if (other is Translation)
                BibleComparator.compare(this.contents, other.contents)
            else
                super.compareTo(other)
}
