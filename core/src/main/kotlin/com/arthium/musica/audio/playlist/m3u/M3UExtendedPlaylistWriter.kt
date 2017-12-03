package com.arthium.musica.audio.playlist.m3u

import com.arthium.musica.audio.playlist.AbstractPlaylistWriter
import com.arthium.musica.audio.playlist.Playlist
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


open class M3UExtendedPlaylistWriter protected constructor(extension: String, charsetName: String, destinationDir: File? = null)
    : AbstractPlaylistWriter(extension, charsetName, destinationDir) {

    private val LOGGER: Logger = LoggerFactory.getLogger(javaClass.`package`.name)

    private val HEADER_TAG = "#EXTM3U"
    private val INFO_TAG = "#EXTINF"

    constructor(destinationDir: File? = null) : this("m3u", "ISO-8859-1", destinationDir)

    override fun write(playlist: Playlist) {


        BufferedWriter(
                OutputStreamWriter(
                        FileOutputStream(File(destinationDir, "${playlist.name}.$extension")),
                        charset
                )
        ).use {

            with(it) {

                write(HEADER_TAG)
                newLine()

                playlist.tracks.forEach { track ->

                    write("$INFO_TAG:${track.duration / 1000},${track.title}")
                    newLine()
                    write(track.uri)
                    newLine()
                }
            }
        }
    }
}