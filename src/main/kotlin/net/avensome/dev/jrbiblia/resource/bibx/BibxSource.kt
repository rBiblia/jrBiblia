package net.avensome.dev.jrbiblia.resource.bibx

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class BibxSource private constructor(private val paths: Set<Path>, private val type: Type) {
    enum class Type {
        LOCAL,      // residing in JrBiblia's own directory
        EXTERNAL,   // managed by rBiblia in its directory
    }

    constructor(path: Path, type: Type) : this(setOf(path), type)

    private fun getFileSet(): Set<File> =
            paths.filter { Files.exists(it) && Files.isDirectory(it) }
                    .map { path ->
                        Files.walk(path, 1)
                                .filter { Files.isRegularFile(it) }
                                .filter { it.toString().endsWith(".bibx") }
                                .map(Path::toFile)
                                .collect(Collectors.toSet())
                    }.fold(emptySet(), { set1, set2 -> set1 + set2 })

    fun getResources(): Set<BibxFile> = getFileSet().map { BibxFile(it, type) }.toHashSet()
}

fun Path.toBibxSource(type: BibxSource.Type) = BibxSource(this, type)
