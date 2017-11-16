package com.arthium.musica.audio

import com.sedmelluq.discord.lavaplayer.track.AudioTrack


interface AudioPlayer {

    fun play(track: AudioTrack)

    fun isPaused() : Boolean

    fun pause()

    fun resume()

    fun cleanup()
}