package com.arthium.musica.ui.panel

import com.arthium.musica.event.PlayTrackEvent
import com.googlecode.lanterna.gui2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class PlayerPanel : Panel(LinearLayout(Direction.VERTICAL)) {

    companion object {

        fun create(): Container =
                PlayerPanel().withBorder(Borders.singleLineBevel())
    }

    private val titleLabel: Label
    private val trackProgressBar: ProgressBar
    private val volumeProgressBar: ProgressBar

    init {
        titleLabel = Label("Nothing playing")
        trackProgressBar = ProgressBar(0, 100, 20)
                .setValue(50)
                .setLabelFormat("00:00/00:00")
        volumeProgressBar = ProgressBar(0, 100, 10)
                .setValue(100)

        addComponent(titleLabel)
        addComponent(trackProgressBar)
        addComponent(EmptySpace())
        addComponent(volumeProgressBar)
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
    fun onPlayTrack(event: PlayTrackEvent) {

        titleLabel.text = event.track.info.title
    }
}