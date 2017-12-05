package com.arthium.musica.event

import com.arthium.musica.audio.playlist.Playlist


data class PlaylistCreatedEvent(val playlist: Playlist)