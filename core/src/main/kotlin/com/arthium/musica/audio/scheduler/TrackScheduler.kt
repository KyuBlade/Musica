package com.arthium.musica.audio.scheduler

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.track.ScheduledAudioTrack
import com.arthium.musica.event.SchedulerTrackAdded
import com.arthium.musica.event.SchedulerTrackRemoved
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import org.greenrobot.eventbus.EventBus


class TrackScheduler(private val audioPlayer: AudioPlayer) : AudioEventAdapter() {

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

        EventBus.getDefault().post(SchedulerTrackAdded(scheduledTrack))
    }

    fun remove(index: Int) {

        if (index < 0 || index >= queue.size)
            return

        val removedTrack = queue.removeAt(index)
        removedTrack.previous?.next = removedTrack.next
        removedTrack.next?.previous = removedTrack.previous

        EventBus.getDefault().post(SchedulerTrackRemoved(index, removedTrack))
    }

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

    override fun onTrackStuck(player: AudioPlayer, track: AudioTrack, thresholdMs: Long) {

        skip()
    }

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {

        if (endReason.mayStartNext) {

            skip()
        }
    }
}