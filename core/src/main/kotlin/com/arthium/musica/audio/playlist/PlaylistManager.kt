package com.arthium.musica.audio.playlist

import com.arthium.musica.audio.playlist.mpls.MPLSPlaylistReader
import com.arthium.musica.audio.playlist.mpls.MPLSPlaylistWriter
import com.arthium.musica.event.PlaylistCreatedEvent
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.nio.file.Paths


object PlaylistManager {

    private val PLAYLIST_DIRECTORY: String = Paths.get(System.getProperty("user.dir"), "playlists").toString()

    private val playlists: MutableMap<String, Playlist> = hashMapOf()
    private val playlistWriter = MPLSPlaylistWriter()

    fun load() {

        val playlistDir = File(PLAYLIST_DIRECTORY)
        val playlistReader = MPLSPlaylistReader()

        playlistDir.listFiles({ pathname: File ->

            pathname.extension == "mpls"

        }).forEach { file: File ->
            playlistReader.read(file).let {

                playlists[it.name] = it
            }
        }
    }

    fun save(playlistName: String) {

        playlists[playlistName]?.let { save(it) }
    }

    fun save(playlist: Playlist) {

        playlistWriter.write(playlist)
    }

    fun add(playlist: Playlist) {

        playlists[playlist.name] = playlist
        save(playlist)

        EventBus.getDefault().post(PlaylistCreatedEvent(playlist))
    }

    fun get(): List<Playlist> = playlists.values.toList()

    fun get(name: String): Playlist? = playlists.getOrDefault(name, null)
}