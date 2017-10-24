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
        val rawFiles = BibxProvider.getBibxFiles().toList().sorted()
        clear()
        emitter.onNext(LoadingProgress(0, 0, rawFiles.size))
        var failed = 0
        rawFiles.forEach {
            try {
                partials.add(it.load())
                emitter.onNext(LoadingProgress(partials.size, failed, rawFiles.size))
            } catch (e: Exception) {
                emitter.onNext(LoadingProgress(partials.size, ++failed, rawFiles.size))
            }
        }
        synchronized(this) {
            partials.forEach { put(it, it.contents) }
        }
        emitter.onComplete()
    }, BackpressureStrategy.LATEST)
}

class LoadedBibxFile(file: File, val contents: Bible) : File(file.toURI())

object Deserializer : BibxSerde()

fun File.load(): LoadedBibxFile = LoadedBibxFile(this, Deserializer.deserialize(this))

data class LoadingProgress(val successful: Int, val failed: Int, val total: Int) {
    val processed: Int get() = successful + failed
}
