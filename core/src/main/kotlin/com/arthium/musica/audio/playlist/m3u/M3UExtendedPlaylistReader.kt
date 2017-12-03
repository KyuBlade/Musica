package com.arthium.musica.audio.playlist.m3u

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


open class M3UExtendedPlaylistReader protected constructor(charsetName: String) : AbstractPlaylistReader(charsetName) {

    private val LOGGER: Logger = LoggerFactory.getLogger(javaClass.`package`.name)

    private val HEADER_TAG = "#EXTM3U"
    private val INFO_TAG = "#EXTINF"

    constructor() : this("ISO-8859-1")

    override fun read(file: File): Playlist {

        FileInputStream(file).use {

            return read(file.nameWithoutExtension, it)
        }
    }

    override fun read(playlistName: String, inputStream: InputStream): Playlist {

        val playlist = Playlist(playlistName)
        val reader: BufferedReader = inputStream.bufferedReader(charset)

        // Check header
        val header: String = reader.readLine()
        if (header != HEADER_TAG)
            throw PlaylistReaderException("Unknown playlist header: $header, expecting: $HEADER_TAG")

        do {

            var line: String? = reader.readLine() ?: break

            if (line!!.startsWith(INFO_TAG)) {

                line = line.removePrefix("$INFO_TAG:")

                val infos: List<String> = line.split(',')
                if (infos.size < 2)
                    throw PlaylistReaderException("Malformed playlist file: Missing information for $INFO_TAG tag")

                val duration: Long = try {
                    infos.first().trim().toLong()
                } catch (e: NumberFormatException) {
                    throw PlaylistReaderException("Malformed playlist file: Duration info is not in numeric format: ${infos[0]}")
                }

                val title: String = infos[1].trim()

                val uri: String = reader.readLine() ?: throw PlaylistReaderException("Malformed playlist file: No URI found after info tag")

                val track = PlaylistTrack(title, duration, uri)
                playlist.tracks.add(track)
            }
        } while (true)

        return playlist
    }
}