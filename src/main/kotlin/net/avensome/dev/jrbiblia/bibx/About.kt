package net.avensome.dev.jrbiblia.bibx

import net.avensome.dev.jbibx.model.About
import net.avensome.dev.jrbiblia.util.orIfBlank
import java.util.regex.Pattern

val About.nonEmptyShortName: String
    get() {
        val generatedShortName = name
                .split(Pattern.compile("\\s+"))
                .map { it.replace(Regex.fromLiteral("^[a-zA-Z0-9]+"), "") }
                .filter { it.isNotEmpty() }
                .joinToString("") { it.substring(0, 1) }
        return shortName.orIfBlank(generatedShortName).orIfBlank("?")
    }
