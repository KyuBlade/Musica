package com.arthium.musica

import com.arthium.musica.audio.playlist.Playlist
import com.arthium.musica.audio.playlist.PlaylistReaderException
import com.arthium.musica.audio.playlist.PlaylistTrack
import com.arthium.musica.audio.playlist.mpls.MPLSPlaylistReader
import com.arthium.musica.audio.playlist.mpls.MPLSPlaylistWriter
import com.arthium.musica.data.AudioTrackMock
import org.junit.Assert
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class MPLSPlaylistTests {

    @Test
    fun writeTest() {

        val playlistName = "test_playlist"
        val playlist = Playlist(playlistName)
        playlist.tracks.add(PlaylistTrack(AudioTrackMock("Vonikk - Katana", "Vonikk", 246000L, "https://soundcloud.com/vonikk/vonikk-katana")))
        playlist.tracks.add(PlaylistTrack(AudioTrackMock("Vonikk - Phoenix", "Vonikk", 307000L, "https://soundcloud.com/vonikk/vonikk-phoenix")))
        playlist.tracks.add(PlaylistTrack(AudioTrackMock("Vonikk - Nova", "Vonikk", 349000L, "https://soundcloud.com/vonikk/vonikk-nova")))

        val writer = MPLSPlaylistWriter(Paths.get(System.getProperty("user.dir"), "build", "tmp").toFile())

        writer.write(playlist)

        val generatedPlaylistData: ByteArray = Files.readAllBytes(Paths.get(writer.destinationDir.path, "$playlistName.mpls"))
        val samplePlaylistData: ByteArray = javaClass.getResource("/mpls/playlist-sample.mpls").readBytes()

        Assert.assertTrue("Generated playlist file doesn't match the sample playlist file", Arrays.equals(generatedPlaylistData, samplePlaylistData))
    }

    @Test
    fun readTest() {

        val firstTrackUri = "https://soundcloud.com/vonikk/vonikk-katana"
        val secondTrackUri = "https://soundcloud.com/vonikk/vonikk-phoenix"
        val thirdTrackUri = "https://soundcloud.com/vonikk/vonikk-nova"

        val reader = MPLSPlaylistReader()

        javaClass.getResource("/mpls/playlist-sample.mpls").openStream().use {

            val playlist: Playlist = reader.read("Test", it)

            val tracks: List<PlaylistTrack> = playlist.tracks
            Assert.assertEquals("Didn't loaded all tracks", 3, tracks.size)

            tracks.forEachIndexed { i, track ->

                when (i) {

                    0 -> {
                        Assert.assertEquals("First track title doesn't match", "Vonikk - Katana", track.title)
                        Assert.assertEquals("First track duration doesn't match", 246000, track.duration)
                        Assert.assertEquals("First track uri doesn't match", firstTrackUri, track.uri)
                    }

                    1 -> {
                        Assert.assertEquals("Second track title doesn't match", "Vonikk - Phoenix", track.title)
                        Assert.assertEquals("Second track duration doesn't match", 307000, track.duration)
                        Assert.assertEquals("Second track uri doesn't match", secondTrackUri, track.uri)
                    }

                    3 -> {
                        Assert.assertEquals("Third track title doesn't match", "Vonikk - Nova", track.title)
                        Assert.assertEquals("Third  track duration doesn't match", 349000, track.duration)
                        Assert.assertEquals("Third track uri doesn't match", thirdTrackUri, track.uri)
                    }
                }
            }
        }
    }

    @Test
    fun readExceptionsTest() {

        val reader = MPLSPlaylistReader()

        javaClass.getResource("/mpls/playlist-malformed-duration.mpls").openStream().use {

            try {
                reader.read("Test", it)
                Assert.assertTrue("Exception for malformed duration not thrown", false)
            } catch (e: PlaylistReaderException) {
                println(e)
            }
        }

        javaClass.getResource("/mpls/playlist-missing-uri.mpls").openStream().use {

            try {
                reader.read("Test", it)
                Assert.assertTrue("Exception for missing uri not thrown", false)
            } catch (e: PlaylistReaderException) {
                println(e)
            }
        }

        javaClass.getResource("/mpls/playlist-missing-title.mpls").openStream().use {

            try {
                reader.read("Test", it)
                Assert.assertTrue("Exception for missing title and uri not thrown", false)
            } catch (e: PlaylistReaderException) {
                println(e)
            }
        }
    }
}