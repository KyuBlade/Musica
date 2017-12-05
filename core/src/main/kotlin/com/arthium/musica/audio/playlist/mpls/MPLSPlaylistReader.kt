package com.arthium.musica.audio.playlist.mpls

import com.arthium.musica.audio.playlist.AbstractPlaylistReader
import com.arthium.musica.audio.playlist.Playlist
import com.arthium.musica.audio.playlist.PlaylistReaderException
import com.arthium.musica.audio.playlist.PlaylistTrack
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStream


open class MPLSPlaylistReader protected constructor(charsetName: String) : AbstractPlaylistReader(charsetName) {

    private val LOGGER: Logger = LoggerFactory.getLogger(javaClass.`package`.name)

    constructor() : this("UTF-8")

    override fun read(file: File): Playlist {

        FileInputStream(file).use {

            return read(file.nameWithoutExtension, it)
        }
    }

    override fun read(playlistName: String, inputStream: InputStream): Playlist {

        val playlist = Playlist(playlistName)
        val reader: BufferedReader = inputStream.bufferedReader(charset)

        do {

            var line: String? = reader.readLine() ?: break
            line = line!!.trim()

            if (line.isBlank()) continue

            val duration: Long = try {
                line.trim().toLong()
            } catch (e: NumberFormatException) {
                throw PlaylistReaderException("Malformed playlist file: Duration info is not in numeric format: $line")
            }

            val name = reader.readLine() ?: throw PlaylistReaderException("Malformed playlist file: Expected title, got end of file")
            val uri = reader.readLine() ?: throw PlaylistReaderException("Malformed playlist file: Expected uri, got end of file")

            playlist.tracks.add(PlaylistTrack(name, duration * 1000L, uri.trim()))

        } while (true)

        return playlist
    }
}