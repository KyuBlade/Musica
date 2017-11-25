package com.arthium.musica.audio

import com.arthium.musica.audio.track.CustomAudioTrack


interface AudioPlayer {

    fun play(track: CustomAudioTrack)

    fun isPaused() : Boolean

    fun pause()

    fun resume()

    fun cleanup()
}