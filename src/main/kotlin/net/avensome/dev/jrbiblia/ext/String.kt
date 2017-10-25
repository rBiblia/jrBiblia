package net.avensome.dev.jrbiblia.ext

fun String.orIfBlank(alternative: String): String = if (this.isBlank()) alternative else this
