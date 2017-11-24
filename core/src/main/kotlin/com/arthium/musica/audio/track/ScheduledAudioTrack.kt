package com.arthium.musica.audio.track

import com.sedmelluq.discord.lavaplayer.track.AudioTrack


class ScheduledAudioTrack(track: AudioTrack) : CustomAudioTrack(track) {

    var previous: ScheduledAudioTrack? = null
    var next: ScheduledAudioTrack? = null
}