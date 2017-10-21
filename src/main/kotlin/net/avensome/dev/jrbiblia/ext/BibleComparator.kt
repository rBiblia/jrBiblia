package net.avensome.dev.jrbiblia.ext

import net.avensome.dev.jbibx.model.Bible
import java.util.*

object BibleComparator : Comparator<Bible> {
    override fun compare(o1: Bible?, o2: Bible?): Int {
        return if (o1 == null) {
            if (o2 == null) 0 else -1
        } else if (o2 == null) 1
        else o1.about.name.compareTo(o2.about.name)
    }
}
