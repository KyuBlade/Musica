package com.arthium.musica.audio.playlist.mpls

import com.arthium.musica.audio.playlist.AbstractPlaylistWriter
import com.arthium.musica.audio.playlist.Playlist
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


class MPLSPlaylistWriter(destinationDir: File?) : AbstractPlaylistWriter("mpls", "UTF-8", destinationDir) {

    constructor() : this(null)

    override fun write(playlist: Playlist) {

        BufferedWriter(
                OutputStreamWriter(
                        FileOutputStream(File(destinationDir, "${playlist.name}.$extension")),
                        charset
                )
        ).use {

            with(it) {

                playlist.tracks.forEach { track ->

                    write("${track.duration / 1000}")
                    newLine()
                    write(track.title)
                    newLine()
                    write(track.uri)
                    newLine()
                }
            }
        }
    }
}