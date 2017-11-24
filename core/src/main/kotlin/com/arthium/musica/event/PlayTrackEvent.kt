package com.arthium.musica.event

import com.arthium.musica.audio.track.CustomAudioTrack


data class PlayTrackEvent(val track: CustomAudioTrack)