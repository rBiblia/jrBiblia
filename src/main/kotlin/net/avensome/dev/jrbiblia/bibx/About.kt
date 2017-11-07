package net.avensome.dev.jrbiblia.bibx

import net.avensome.dev.jbibx.model.About
import net.avensome.dev.jrbiblia.util.orIfBlank
import java.util.regex.Pattern

class AboutFallbacks(private val original: About) {
    val shortName
        get() = original.shortName
                .orIfBlank(original.name
                        .split(Pattern.compile("\\s+"))
                        .map { it.replace(Regex.fromLiteral("^[a-zA-Z0-9]+"), "") }
                        .filter { it.isNotEmpty() }
                        .joinToString("") { if (it.toUpperCase() == it) it else it.substring(0, 1) }
                ).orIfBlank("?")

    val name get() = original.name.orIfBlank(original.shortName).orIfBlank("?")
}

val About.withFallback get() = AboutFallbacks(this)
