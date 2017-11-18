package com.arthium.musica

import com.sedmelluq.discord.lavaplayer.track.AudioTrack


object TrackScheduler {

    private val queue: MutableList<AudioTrack> = mutableListOf()

    fun add(track: AudioTrack) {

        queue.add(track)
    }

    fun remove(track: AudioTrack) {

        queue.remove(track)
    }

    fun remove(index: Int) {

        queue.removeAt(index)
    }
}