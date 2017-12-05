package com.arthium.musica.audio.playlist

import java.io.File
import java.nio.charset.Charset


abstract class AbstractPlaylistWriter(
        val extension: String,
        charsetName: String = "ISO-8859-1",
        destinationDir: File?
) {

    val charset: Charset = Charset.forName(charsetName)
    val destinationDir: File = destinationDir ?: File(System.getProperty("user.dir"), "playlists")

    init {

        if (!this.destinationDir.exists())
            this.destinationDir.mkdirs()
    }

    abstract fun write(playlist: Playlist)
}