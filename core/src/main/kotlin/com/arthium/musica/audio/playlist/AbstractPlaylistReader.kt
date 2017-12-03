package com.arthium.musica.audio.playlist

import java.io.File
import java.io.InputStream
import java.nio.charset.Charset


abstract class AbstractPlaylistReader(charsetName: String = "ISO-8859-1") {

    val charset: Charset = Charset.forName(charsetName)

    abstract fun read(file: File): Playlist

    abstract fun read(playlistName: String, inputStream: InputStream): Playlist
}