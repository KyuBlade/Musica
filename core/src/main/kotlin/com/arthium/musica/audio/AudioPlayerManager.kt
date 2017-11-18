package com.arthium.musica.audio

import com.arthium.musica.TrackScheduler
import com.arthium.musica.event.PlayTrackEvent
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat
import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit


object AudioPlayerManager : com.arthium.musica.audio.AudioPlayer {

    val playerManager: DefaultAudioPlayerManager = DefaultAudioPlayerManager()
    val audioPlayer: AudioPlayer
    val trackScheduler: TrackScheduler

    init {
        playerManager.registerSourceManager(YoutubeAudioSourceManager())
        playerManager.registerSourceManager(SoundCloudAudioSourceManager())

        playerManager.configuration.outputFormat =
                AudioDataFormat(2, 44100, 960, AudioDataFormat.Codec.PCM_S16_BE)
        playerManager.configuration.resamplingQuality = AudioConfiguration.ResamplingQuality.HIGH
        playerManager.frameBufferDuration = TimeUnit.SECONDS.toMillis(1).toInt()

        trackScheduler = TrackScheduler()

        audioPlayer = playerManager.createPlayer()
    }

    override fun play(track: AudioTrack) {

        audioPlayer.playTrack(track)

        EventBus.getDefault().post(PlayTrackEvent(track))
    }

    fun search(query: String, loadCallback: AudioLoadResultHandler) {

        val resultHandler: AudioLoadResultHandler = SearchAudioResultHandler(loadCallback)

        playerManager.loadItemOrdered(query, "ytsearch:$query", resultHandler)

        for (i in 0..500 step 50) {
            playerManager.loadItemOrdered(query, "scsearch[$i,50]:$query", resultHandler)
        }
    }

    override fun isPaused(): Boolean =
            audioPlayer.isPaused

    override fun pause() {

        audioPlayer.isPaused = true
    }

    override fun resume() {

        audioPlayer.isPaused = false
    }

    override fun cleanup() {
        audioPlayer.destroy()
    }
}