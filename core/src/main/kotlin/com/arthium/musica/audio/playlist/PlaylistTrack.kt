package com.arthium.musica.audio.playlist

import com.sedmelluq.discord.lavaplayer.track.AudioTrack


class PlaylistTrack(val title: String, val duration: Long, val uri: String) {

    constructor(track: AudioTrack) : this(track.info.title, track.duration, track.info.uri)
}