package com.arthium.musica.ui.panel.scheduler

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

    private val schedulerTable: TrackSchedulerTable

    init {

        schedulerTable = TrackSchedulerTable()

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

        val track = event.track

        schedulerTable.addTrack(track)
    }

    @Subscribe
    fun onSchedulerTrackRemoved(event: SchedulerTrackRemoved) {

        schedulerTable.removeAt(event.index)
    }
}