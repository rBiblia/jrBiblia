package net.avensome.dev.jrbiblia.bibx

import net.avensome.dev.jbibx.model.Bible
import net.avensome.dev.jrbiblia.ext.BibleComparator
import java.io.File

class Translation(file: File, val contents: Bible) : File(file.toURI()) {
    override fun compareTo(other: File?): Int =
            if (other is Translation)
                BibleComparator.compare(this.contents, other.contents)
            else
                super.compareTo(other)
}
