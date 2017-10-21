package net.avensome.dev.jrbiblia.bibx

import org.apache.commons.lang3.SystemUtils
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

object BibxProvider {
    private fun getAppData(): Path =
            Paths.get(System.getenv("APPDATA") ?: throw UnsupportedPlatformException())

    private fun getHome(): Path = Paths.get(System.getProperty("user.home"))

    private fun getLocalSource(): Path =
            if (SystemUtils.IS_OS_WINDOWS)
                getAppData().resolve("JrBiblia\\bibles")
            else
                getHome().resolve(".JrBiblia/Bibles")

    private fun getExternalSource(): Path =
            getAppData().resolve("rBiblia\\bibx")

    fun getBibxFiles(): Set<File> =
            if (SystemUtils.IS_OS_WINDOWS)
                getLocalSource().getFileSet() + getExternalSource().getFileSet()
            else
                getLocalSource().getFileSet()
}

class UnsupportedPlatformException : Exception()

fun Path.getFileSet(): Set<File> =
        if (Files.exists(this) && Files.isDirectory(this))
            Files.walk(this, 1)
                    .filter { Files.isRegularFile(it) }
                    .filter { it.toString().endsWith(".bibx") }
                    .map(Path::toFile)
                    .collect(Collectors.toSet())
        else emptySet()
