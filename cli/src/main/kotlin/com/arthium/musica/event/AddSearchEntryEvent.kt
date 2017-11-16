package com.arthium.musica.event

import com.sedmelluq.discord.lavaplayer.track.AudioTrack


data class AddSearchEntryEvent(val track: AudioTrack)