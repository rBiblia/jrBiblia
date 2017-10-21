package net.avensome.dev.jrbiblia.resource.bibx

import org.apache.commons.lang3.SystemUtils
import java.nio.file.Path
import java.nio.file.Paths

object BibxProvider {
    private fun getAppData(): Path =
            Paths.get(System.getenv("APPDATA") ?: throw UnsupportedPlatformException())

    private fun getHome(): Path = Paths.get(System.getProperty("user.home"))

    private fun getLocalSource(): BibxSource =
            if (SystemUtils.IS_OS_WINDOWS)
                getAppData().resolve("JrBiblia\\bibles").toBibxSource(BibxSource.Type.LOCAL)
            else
                getHome().resolve(".JrBiblia/Bibles").toBibxSource(BibxSource.Type.LOCAL)

    private fun getExternalSource(): BibxSource =
            getAppData().resolve("rBiblia\\bibx").toBibxSource(BibxSource.Type.EXTERNAL)

    fun getBibxFiles(): Set<BibxFile> =
            if (SystemUtils.IS_OS_WINDOWS)
                getLocalSource().getResources() + getExternalSource().getResources()
            else
                getLocalSource().getResources()
}

class UnsupportedPlatformException : Exception()
