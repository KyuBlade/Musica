package com.arthium.musica.audio

import com.arthium.musica.event.AddSearchEntryEvent
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import org.greenrobot.eventbus.EventBus


class SearchResultHandler : AudioLoadResultHandler {

    override fun loadFailed(exception: FriendlyException?) {
    }

    override fun trackLoaded(track: AudioTrack) {

        EventBus.getDefault().post(AddSearchEntryEvent(track))
    }

    override fun noMatches() {
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {

        playlist?.tracks?.forEach {

            EventBus.getDefault().post(AddSearchEntryEvent(it))
        }
    }
}