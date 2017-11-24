package com.arthium.musica.audio

import com.arthium.musica.audio.track.CustomAudioTrack
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat
import com.sedmelluq.discord.lavaplayer.format.AudioPlayerInputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine


object DesktopAudioPlayer : AudioPlayer {

    private var sourceLine: SourceDataLine? = null
    private val playbackExecutor: ExecutorService

    init {

        playbackExecutor = Executors.newSingleThreadExecutor { r ->
            val thread = Executors.defaultThreadFactory().newThread(r)
            thread.name = "Playback thread"
            thread.isDaemon = true

            thread
        }

        playbackExecutor.execute {

            runPlayback()
        }
    }

    override fun play(track: CustomAudioTrack) {

        AudioPlayerManager.play(track)
    }

    override fun isPaused(): Boolean =
            AudioPlayerManager.isPaused()

    override fun pause() {

        AudioPlayerManager.pause()
        sourceLine?.stop()
    }

    override fun resume() {

        AudioPlayerManager.resume()
        sourceLine?.start()
    }

    override fun cleanup() {

        sourceLine?.let {

            with(sourceLine!!) {

                stop()
                drain()
                close()
            }
        }

    }

    private fun runPlayback() {

        val format: AudioDataFormat = AudioPlayerManager.playerManager.configuration.outputFormat
        val stream: AudioInputStream = AudioPlayerInputStream.createStream(AudioPlayerManager.audioPlayer, format, 0, false)
        val info: DataLine.Info = DataLine.Info(SourceDataLine::class.java, stream.format)
        sourceLine = AudioSystem.getLine(info) as SourceDataLine

        if (sourceLine == null)
            return

        sourceLine!!.open(stream.format)
        sourceLine!!.start()


        val buffer = ByteArray(format.bufferSize(2))
        var chunkSize: Int

        do {
            chunkSize = stream.read(buffer)
            if (chunkSize >= 0) {
                sourceLine!!.write(buffer, 0, chunkSize)
            }
        } while (true)
    }
}