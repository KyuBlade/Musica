package com.arthium.musica.audio.playlist

import java.io.File


object PlaylistManager {

    private val PLAYLIST_DIRECTORY: String = System.getProperty("user.home")

    private val playlists: MutableMap<String, Playlist> = hashMapOf()

    fun load() {

        val playlistDir = File(PLAYLIST_DIRECTORY)

        playlistDir.listFiles({

            pathname: File ->
            pathname.extension == "m3u"
        }).forEach {

            file: File ->
            {

                val playlistName = file.nameWithoutExtension
                // Parse and load m3u file
            }
        }
    }

    fun save(playlistName: String) {

        playlists[playlistName]?.let { save(it) }
    }

    fun save(playlist: Playlist) {


    }
}