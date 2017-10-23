package net.avensome.dev.jrbiblia.bibx

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import net.avensome.dev.jbibx.model.Bible
import net.avensome.dev.jbibx.serde.BibxSerde
import java.io.File
import java.util.*

object BibxCache : HashMap<LoadedBibxFile, Bible>() {
    fun rebuild(): Flowable<LoadingProgress> = Flowable.create({ emitter ->
        val partials = LinkedList<LoadedBibxFile>()
        val rawFiles = BibxProvider.getBibxFiles()
        emitter.onNext(LoadingProgress(0, rawFiles.size))
        rawFiles.forEach {
            partials.add(it.load())
            emitter.onNext(LoadingProgress(partials.size, rawFiles.size))
        }
        synchronized(this) {
            clear()
            partials.forEach { put(it, it.contents) }
        }
        emitter.onComplete()
    }, BackpressureStrategy.LATEST)
}

class LoadedBibxFile(file: File, val contents: Bible) : File(file.toURI())

object Deserializer : BibxSerde()

fun File.load(): LoadedBibxFile = LoadedBibxFile(this, Deserializer.deserialize(this))

data class LoadingProgress(val loaded: Int, val total: Int)
