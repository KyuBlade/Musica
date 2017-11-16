package com.arthium.musica.event

import com.sedmelluq.discord.lavaplayer.track.AudioTrack


data class PlayTrackEvent(val track: AudioTrack)