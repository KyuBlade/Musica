package com.arthium.musica.audio.playlist


data class Playlist(var name: String) {

    val tracks: MutableList<PlaylistTrack> = arrayListOf()
}