package com.arthium.musica.ui.panel.scheduler

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.track.ScheduledAudioTrack
import com.arthium.musica.event.PlayTrackEvent
import com.arthium.musica.event.SchedulerTrackAdded
import com.arthium.musica.event.SchedulerTrackRemoved
import com.googlecode.lanterna.gui2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class TrackSchedulerPanel private constructor() : Panel(BorderLayout()) {

    companion object {

        fun create(): Border =
                TrackSchedulerPanel().withBorder(Borders.singleLine("Scheduler"))
    }

    private val schedulerTable: TrackSchedulerTable = TrackSchedulerTable()

    init {

        addComponent(schedulerTable, BorderLayout.Location.CENTER)
    }

    override fun onAdded(container: Container?) {
        super.onAdded(container)

        EventBus.getDefault().register(this)
    }

    override fun onRemoved(container: Container?) {
        super.onRemoved(container)

        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onSchedulerTrackAdded(event: SchedulerTrackAdded) {

        val track = event.audioTrack

        schedulerTable.addTrack(track.track)
    }

    @Subscribe
    fun onSchedulerTrackRemoved(event: SchedulerTrackRemoved) {

        schedulerTable.removeAt(event.index)
    }

    @Subscribe
    fun onPlayTrack(event: PlayTrackEvent) {

        if (event.track is ScheduledAudioTrack) {

            val index = AudioPlayerManager.trackScheduler.indexOf(event.track as ScheduledAudioTrack)
            schedulerTable.selectedRow = index
        }
    }
}