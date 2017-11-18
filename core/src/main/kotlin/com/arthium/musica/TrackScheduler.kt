package com.arthium.musica

import com.arthium.musica.event.SchedulerTrackAdded
import com.arthium.musica.event.SchedulerTrackRemoved
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import org.greenrobot.eventbus.EventBus


class TrackScheduler {

    private val queue: MutableList<AudioTrack> = mutableListOf()

    fun add(track: AudioTrack) {

        queue.add(track)
        EventBus.getDefault().post(SchedulerTrackAdded(track))
    }

    fun remove(track: AudioTrack) {

        remove(queue.indexOf(track))
    }

    fun remove(index: Int) {

        val removedTrack = queue.removeAt(index)
        EventBus.getDefault().post(SchedulerTrackRemoved(index, removedTrack))
    }

    fun get(index: Int) =
            queue[index]
}