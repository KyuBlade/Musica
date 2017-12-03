package com.arthium.musica.data

import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo
import com.sedmelluq.discord.lavaplayer.track.AudioTrackState
import com.sedmelluq.discord.lavaplayer.track.TrackMarker


class AudioTrackMock(title: String, author: String, duration: Long, uri: String) : AudioTrack {

    val _info: AudioTrackInfo = AudioTrackInfo(title, author, duration, "", false, uri)

    override fun setMarker(marker: TrackMarker?) {
    }

    override fun getState(): AudioTrackState = AudioTrackState.INACTIVE

    override fun getInfo(): AudioTrackInfo = _info

    override fun getDuration(): Long = _info.length

    override fun getIdentifier(): String = ""

    override fun isSeekable(): Boolean = true

    override fun getPosition(): Long = 0

    override fun getSourceManager(): AudioSourceManager? = null

    override fun makeClone(): AudioTrack = this

    override fun setUserData(userData: Any?) {
    }

    override fun getUserData(): Any? = null

    override fun <T : Any?> getUserData(klass: Class<T>?): T? = null

    override fun stop() {
    }

    override fun setPosition(position: Long) {
    }
}