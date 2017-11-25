package com.arthium.musica.audio

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack


class SearchAudioResultHandler(private var callback: AudioLoadResultHandler) : AudioLoadResultHandler {

    override fun loadFailed(exception: FriendlyException?) {
        println(exception?.message)

        callback.loadFailed(exception)
    }

    override fun trackLoaded(track: AudioTrack?) {
        println("Loaded audioTrack ${track?.info?.title}")

        callback.trackLoaded(track)
    }

    override fun noMatches() {
        println("Nothing found")

        callback.noMatches()
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {
        println("Loaded playlist ${playlist?.name} (${playlist?.tracks?.size})")
        playlist?.tracks?.forEach { println(it.info.title) }

        callback.playlistLoaded(playlist)
    }
}