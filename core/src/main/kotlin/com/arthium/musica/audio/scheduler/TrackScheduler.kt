package com.arthium.musica.audio.scheduler

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.playlist.Playlist
import com.arthium.musica.audio.track.ScheduledAudioTrack
import com.arthium.musica.event.*
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import org.greenrobot.eventbus.EventBus
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class TrackScheduler(private val audioPlayer: AudioPlayer) : AudioEventAdapter() {

    private val LOGGER: Logger = LoggerFactory.getLogger(javaClass)

    private val queue: MutableList<ScheduledAudioTrack> = mutableListOf()
    var currentTrack: ScheduledAudioTrack? = null

    var scheduling: Boolean = false

    fun add(track: AudioTrack) {

        val scheduledTrack = ScheduledAudioTrack(track)

        val previousTrack: ScheduledAudioTrack? = if (queue.size > 0) queue.last() else null
        previousTrack?.next = scheduledTrack
        scheduledTrack.previous = previousTrack

        if (count() == 0) {

            currentTrack = scheduledTrack
        }

        queue.add(scheduledTrack)

        EventBus.getDefault().post(TrackSchedulerAddEvent(scheduledTrack))
    }

    fun set(playlist: Playlist) {

        clear()
        add(playlist)
    }

    fun add(playlist: Playlist) {

        playlist.tracks.forEach {

            AudioPlayerManager.playerManager.loadItemOrdered(playlist, it.uri, object : AudioLoadResultHandler {

                override fun loadFailed(exception: FriendlyException?) {
                    LOGGER.warn("Unable to load track ${it.title} (${it.uri})", exception)
                }

                override fun trackLoaded(track: AudioTrack) {

                    add(track)
                }

                override fun noMatches() {
                    LOGGER.warn("Track ${it.title} (${it.uri}) not found")
                }

                override fun playlistLoaded(playlist: AudioPlaylist) {
                    // TODO: Management of playlist in playlists?
                }
            })
        }
    }

    fun remove(index: Int) {

        if (index < 0 || index >= queue.size)
            return

        val removedTrack = queue.removeAt(index)

        removedTrack.previous?.next = removedTrack.next
        removedTrack.next?.previous = removedTrack.previous

        removedTrack.previous = null
        removedTrack.next = null

        EventBus.getDefault().post(TrackSchedulerRemoveEvent(index, removedTrack))
    }

    fun get(): List<ScheduledAudioTrack> = queue.toList()

    fun get(index: Int): ScheduledAudioTrack? {

        return if (index < 0 || index >= queue.size) null
        else queue[index]
    }


    fun indexOf(track: ScheduledAudioTrack): Int =
            queue.indexOf(track)

    fun count(): Int =
            queue.size

    fun getPreviousTrack(): ScheduledAudioTrack? =
            when {
                currentTrack?.previous != null -> currentTrack?.previous!!
                queue.isEmpty() -> null
                else -> queue.last()
            }

    fun getNextTrack(): ScheduledAudioTrack? =
            when {
                currentTrack?.next != null -> currentTrack?.next!!
                queue.isEmpty() -> null
                else -> queue.first()
            }

    fun skip(forward: Boolean = true) {

        if (scheduling) {

            currentTrack = if (forward) getNextTrack() else getPreviousTrack()
            currentTrack?.let { AudioPlayerManager.play(currentTrack!!) }
        }
    }

    fun clear() {

        queue.clear()
        EventBus.getDefault().post(TrackSchedulerClearEvent())
    }

    override fun onPlayerResume(player: AudioPlayer) {

        EventBus.getDefault().post(PlayerResumeEvent())
    }

    override fun onPlayerPause(player: AudioPlayer) {

        EventBus.getDefault().post(PlayerPauseEvent())
    }

    override fun onTrackStuck(player: AudioPlayer, track: AudioTrack, thresholdMs: Long) {

        skip()
    }

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {

        if (endReason.mayStartNext) {

            skip()
        }
    }
}