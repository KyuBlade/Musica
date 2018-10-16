package com.arthium.musica

import com.arthium.musica.audio.playlist.Playlist
import com.arthium.musica.audio.playlist.PlaylistReaderException
import com.arthium.musica.audio.playlist.PlaylistTrack
import com.arthium.musica.audio.playlist.m3u.M3UExtendedPlaylistReader
import com.arthium.musica.audio.playlist.m3u.M3UExtendedPlaylistWriter
import com.arthium.musica.audio.playlist.m3u8.M3U8ExtendedPlaylistWriter
import com.arthium.musica.data.AudioTrackMock
import org.junit.Assert
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class M3UPlaylistTests {

    @Test
    fun writeM3UTest() {

        val playlistName = "test_playlist"
        val playlist = Playlist(playlistName)
        playlist.tracks.add(PlaylistTrack(AudioTrackMock("Vonikk - Katana", "Vonikk", 246000L, "https://cf-media.sndcdn.com/rrOCBeEgUoHD.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vcnJPQ0JlRWdVb0hELjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1MTIyNDgzMDZ9fX1dfQ__&Signature=S6iGcm2g65fvKxzCmFbFk41~tqu0efHWVQOpdd4uhKs4btRoqbKgew0xzvZWglgNwUMZvq65e5ZCc12V0eQ3DNWxBnIDtYChG0gqHtGkwoTEwQb5jlzz8eJp2KRjTMGqgokugwTh4rizzYRKcxjZVoyGOr2kyf6Qs6p8GyORAemz3xpOjX69V~pI9UjXQlY2SJ8LrJhN8Ia9TasBVo5WvmZmZmOUeERLs2qe6uKkiJCyjgRO7elE3IAMCsDButil~Xvo5ZJq2sZp6cu263F4a0IjXaKtlCADnmbgPl6YY3-rNWBvkd6hfECoSbIyMpXYGTMb3~AWSUvHyJW9N3-gIA__&Key-Pair-Id=APKAJAGZ7VMH2PFPW6UQ")))
        playlist.tracks.add(PlaylistTrack(AudioTrackMock("Vonikk - Phoenix", "Vonikk", 307000L, "https://cf-media.sndcdn.com/QLJWVmUg8gri.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vUUxKV1ZtVWc4Z3JpLjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1MTIyNDg0Njd9fX1dfQ__&Signature=phiAN-ER3VvtOj0SEP~yTsWGmW6lJq4YDEUJOPsPsLQuKlxnSOAjKrttaGCIjIMOfTcVbikA2NddPM0E9whWM-p3hOCz32lHt2m6C~RYDMRb9OJ~FqMvSsyC2yceIDGSkS6~kovN-L-VJAm9TF5h3cL~NSA5dTqUt5vlwk0GxVDUNq79V2MWYbq160OOAh9EOPOssRWmhTgXZgxqqP~YYtFzlWdnJO~Ypmbox62FYUrgrQ~B0cyC8m1-dSYTZpe-YaKE2vzlx5bzdrSQiNBpsvppccjmBexo1aKj6UDMTxFwkyEXgSTgbdxM61DgMmPwZUjiGRRdtMQssWMO2bTgKQ__&Key-Pair-Id=APKAJAGZ7VMH2PFPW6UQ")))
        playlist.tracks.add(PlaylistTrack(AudioTrackMock("Vonikk - Nova", "Vonikk", 349000L, "https://cf-media.sndcdn.com/bRudZgZfrfNW.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vYlJ1ZFpnWmZyZk5XLjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1MTIyNDg3Mzl9fX1dfQ__&Signature=Ou3Mkv0V4zRC~iX25sx1A1~b~b1W3T5sDNm4p6hsVMweQI~ja71MIZIscjU6xAL9G6vqeU9gYQp4jVcsYlbb3YY9QyIPqtmltNYCH61gKoIzayGFfzda56~EdE5DyAXh4pZZnZNlowfBIEGZcAgInUSqmnXSPfJdpdKzTJEulV22oOvRIYK-H5lEKFteOHiwrc8T-yyae97OWOTdAhwLjQME4TYQTpLi053PGRVqZneCUvEf0c32hH3ijE0j2B0I5eIoQwH95Ulh3tq-aOyAvk1aXON03kGWBDs8FVUOpxokLa57D298Gd4ln1ysh6IaZH8k9jDMw~GjFwwQQEo~tQ__&Key-Pair-Id=APKAJAGZ7VMH2PFPW6UQ")))

        val writer = M3UExtendedPlaylistWriter(Paths.get(System.getProperty("user.dir"), "build", "tmp").toFile())

        writer.write(playlist)

        val generatedPlaylistData: ByteArray = Files.readAllBytes(Paths.get(writer.destinationDir.path, "$playlistName.m3u"))
        val samplePlaylistData: ByteArray = javaClass.getResource("/m3u/playlist-sample.m3u").readBytes()

        Assert.assertTrue("Generated playlist file doesn't match the sample playlist file", Arrays.equals(generatedPlaylistData, samplePlaylistData))
    }

    @Test
    fun writeM3U8Test() {

        val playlistName = "test_playlist"
        val playlist = Playlist(playlistName)
        playlist.tracks.add(PlaylistTrack(AudioTrackMock("【初音ミク】 メルト／Melt 【VOCALOID カバー】", "Hatsune Miku", 246000L, "https://cf-media.sndcdn.com/ZzODx62sh4VE.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vWnpPRHg2MnNoNFZFLjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1MTIzMzAyODJ9fX1dfQ__&Signature=ZRuKmwgAseYTM3-Uunz6oc~UVCfIyD1rAKlPetx99lGenF8gx7b4D3AHlyBLqFJWmV23R6XAZ73XYHdGHdcAFExWRmFrE-JMZukrGy1d69qEgu~EWyFua~qFENcfzp5kesOPbV1Kx83XxDi2gJ8iNs7i4miNFdvKe1KNwfQfU1kmXu9TFlPSSDvn8JaXgTcEjyNG400tibY9po8M8SpJGzxZGXo~CUZ75ji0qQUWv~-CdUTqP0bcXfcBEoc4QyzGDAS7RYbNILv34N7c9x0PDkTIKwT6YhT93jkZBMx6a5Kujon8dinbR~jTpn4cU6~mlZ6~OBgR0gp4Oe6Rz~uUqQ__&Key-Pair-Id=APKAJAGZ7VMH2PFPW6UQ")))
        playlist.tracks.add(PlaylistTrack(AudioTrackMock("【鏡音リン / Rin Kagamine】 Happy Halloween", "Rin Kagamine", 307000L, "https://cf-media.sndcdn.com/jOowYZlNC0zD.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vak9vd1labE5DMHpELjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1MTIzMzAzODl9fX1dfQ__&Signature=jUIpv0TufWn9B1LI1yanfC7PcUMyu5bB8J0rwkLdJZdLKBrKrXDkp~STsSbCsU9tYk3JWZz1Hxkn6oN2URkvd6apxVHyERojdor6CNQE9-fmlzdyMXQDbyUaLPkivJpfS-MP2Pm0CHE3bdq2LT74x5tue4hpODTzMk0XyMBj3mxFXGibRgv9bn0TOlYNAwHpOdrAypn0gdm7Yg5CTu4iW5GVzsCIfEkQWPXpAi-Qj4QHJ4yHoMl0DA6l20TQCsKvoCh3xiIN3cHi5A4xxB2TqyfbTrUWnKyhkykK7SxfnrvBppr95JIF1T81QBK1o1hhtTUNDjHDafH5a-q~4yxmMA__&Key-Pair-Id=APKAJAGZ7VMH2PFPW6UQ")))

        val writer = M3U8ExtendedPlaylistWriter(Paths.get(System.getProperty("user.dir"), "build", "tmp").toFile())

        writer.write(playlist)

        val generatedPlaylistData: ByteArray = Files.readAllBytes(Paths.get(writer.destinationDir.path, "$playlistName.m3u8"))
        val samplePlaylistData: ByteArray = javaClass.getResource("/m3u/playlist-sample.m3u8").readBytes()

        Assert.assertTrue("Generated playlist file doesn't match the sample playlist file", Arrays.equals(generatedPlaylistData, samplePlaylistData))
    }

    @Test
    fun readTest() {

        val firstTrackUri = "https://cf-media.sndcdn.com/rrOCBeEgUoHD.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vcnJPQ0JlRWdVb0hELjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1MTIyNDgzMDZ9fX1dfQ__&Signature=S6iGcm2g65fvKxzCmFbFk41~tqu0efHWVQOpdd4uhKs4btRoqbKgew0xzvZWglgNwUMZvq65e5ZCc12V0eQ3DNWxBnIDtYChG0gqHtGkwoTEwQb5jlzz8eJp2KRjTMGqgokugwTh4rizzYRKcxjZVoyGOr2kyf6Qs6p8GyORAemz3xpOjX69V~pI9UjXQlY2SJ8LrJhN8Ia9TasBVo5WvmZmZmOUeERLs2qe6uKkiJCyjgRO7elE3IAMCsDButil~Xvo5ZJq2sZp6cu263F4a0IjXaKtlCADnmbgPl6YY3-rNWBvkd6hfECoSbIyMpXYGTMb3~AWSUvHyJW9N3-gIA__&Key-Pair-Id=APKAJAGZ7VMH2PFPW6UQ"
        val secondTrackUri = "https://cf-media.sndcdn.com/QLJWVmUg8gri.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vUUxKV1ZtVWc4Z3JpLjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1MTIyNDg0Njd9fX1dfQ__&Signature=phiAN-ER3VvtOj0SEP~yTsWGmW6lJq4YDEUJOPsPsLQuKlxnSOAjKrttaGCIjIMOfTcVbikA2NddPM0E9whWM-p3hOCz32lHt2m6C~RYDMRb9OJ~FqMvSsyC2yceIDGSkS6~kovN-L-VJAm9TF5h3cL~NSA5dTqUt5vlwk0GxVDUNq79V2MWYbq160OOAh9EOPOssRWmhTgXZgxqqP~YYtFzlWdnJO~Ypmbox62FYUrgrQ~B0cyC8m1-dSYTZpe-YaKE2vzlx5bzdrSQiNBpsvppccjmBexo1aKj6UDMTxFwkyEXgSTgbdxM61DgMmPwZUjiGRRdtMQssWMO2bTgKQ__&Key-Pair-Id=APKAJAGZ7VMH2PFPW6UQ"
        val thirdTrackUri = "https://cf-media.sndcdn.com/bRudZgZfrfNW.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vYlJ1ZFpnWmZyZk5XLjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1MTIyNDg3Mzl9fX1dfQ__&Signature=Ou3Mkv0V4zRC~iX25sx1A1~b~b1W3T5sDNm4p6hsVMweQI~ja71MIZIscjU6xAL9G6vqeU9gYQp4jVcsYlbb3YY9QyIPqtmltNYCH61gKoIzayGFfzda56~EdE5DyAXh4pZZnZNlowfBIEGZcAgInUSqmnXSPfJdpdKzTJEulV22oOvRIYK-H5lEKFteOHiwrc8T-yyae97OWOTdAhwLjQME4TYQTpLi053PGRVqZneCUvEf0c32hH3ijE0j2B0I5eIoQwH95Ulh3tq-aOyAvk1aXON03kGWBDs8FVUOpxokLa57D298Gd4ln1ysh6IaZH8k9jDMw~GjFwwQQEo~tQ__&Key-Pair-Id=APKAJAGZ7VMH2PFPW6UQ"

        val reader = M3UExtendedPlaylistReader()

        javaClass.getResource("/m3u/playlist-sample.m3u").openStream().use {

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

        val reader = M3UExtendedPlaylistReader()

        javaClass.getResource("/m3u/playlist-wrong-header.m3u").openStream().use {
            try {
                reader.read("Test", it)
                Assert.assertTrue("Exception for wrong header not thrown", false)

            } catch (e: PlaylistReaderException) {
                println(e)
            }
        }

        javaClass.getResource("/m3u/playlist-missing-info.m3u").openStream().use {

            try {
                reader.read("Test", it)
                Assert.assertTrue("Exception for missing info in info tag not thrown", false)

            } catch (e: PlaylistReaderException) {
                println(e)
            }
        }

        javaClass.getResource("/m3u/playlist-malformed-duration.m3u").openStream().use {

            try {
                reader.read("Test", it)
                Assert.assertTrue("Exception for malformed duration info not thrown", false)

            } catch (e: PlaylistReaderException) {
                println(e)
            }
        }

        javaClass.getResource("/m3u/playlist-missing-uri.m3u").openStream().use {

            try {
                reader.read("Test", it)
                Assert.assertTrue("Exception for missing uri not thrown", false)

            } catch (e: PlaylistReaderException) {
                println(e)
            }
        }
    }
}