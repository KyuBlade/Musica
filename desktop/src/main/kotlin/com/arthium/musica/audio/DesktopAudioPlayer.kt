package com.arthium.musica.audio

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat
import com.sedmelluq.discord.lavaplayer.format.AudioPlayerInputStream
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine


object DesktopAudioPlayer : AudioPlayer {

    private var sourceLine: SourceDataLine? = null
    private val playbackExecutor: Executor

    init {

        playbackExecutor = Executors.newSingleThreadExecutor()

        playbackExecutor.execute {

            runPlayback()
        }
    }

    override fun play(track: AudioTrack) {

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

        with(sourceLine!!) {

            stop()
            drain()
            close()
        }
    }

    private fun runPlayback() {

        val format: AudioDataFormat = AudioPlayerManager.playerManager.configuration.outputFormat
        val stream: AudioInputStream = AudioPlayerInputStream.createStream(AudioPlayerManager.audioPlayer, format, 0, false)
        val info: DataLine.Info = DataLine.Info(SourceDataLine::class.java, stream.format)
        sourceLine = AudioSystem.getLine(info) as SourceDataLine

        if(sourceLine == null)
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

        // Thread blocking application exit
        println("Exit Playback thread")
    }
}