package com.arthium.musica.audio.playlist.m3u8

import com.arthium.musica.audio.playlist.m3u.M3UExtendedPlaylistWriter
import java.io.File


class M3U8ExtendedPlaylistWriter(destinationDir: File? = null) : M3UExtendedPlaylistWriter("m3u8", "UTF-8", destinationDir)