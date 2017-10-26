package net.avensome.dev.jrbiblia.util

fun String.orIfBlank(alternative: String): String = if (this.isBlank()) alternative else this
