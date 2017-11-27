package com.arthium.musica.ui.panel

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.event.PlayerPauseEvent
import com.arthium.musica.event.PlayerResumeEvent
import com.arthium.musica.event.TrackPlayEvent
import com.arthium.musica.utils.StringUtils
import com.googlecode.lanterna.gui2.*
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit


class PlayerPanel : Panel(LinearLayout(Direction.VERTICAL)) {

    companion object {

        fun create(): Container =
                PlayerPanel().withBorder(Borders.singleLineBevel())
    }

    private val titleLabel: Label
    private val trackProgressBar: ProgressBar
    private val volumeProgressBar: ProgressBar

    private val playerUpdateExecutor: ScheduledExecutorService
    private var playerUpdateFuture: ScheduledFuture<*>? = null

    init {
        titleLabel = Label("Nothing playing")
        trackProgressBar = ProgressBar(0, 100, 20)
                .setLabelFormat("-")
        volumeProgressBar = ProgressBar(0, 100, 10)
                .setValue(100)

        addComponent(titleLabel)
        addComponent(trackProgressBar)
        addComponent(EmptySpace())
        addComponent(volumeProgressBar)

        playerUpdateExecutor = Executors.newSingleThreadScheduledExecutor { r ->
            val thread = Executors.defaultThreadFactory().newThread(r)
            thread.name = "Player UI Updater"
            thread.isDaemon = true

            thread
        }
    }

    private val playerUpdateTask: Runnable = Runnable {

        val playingTrack: AudioTrack? = AudioPlayerManager.audioPlayer.playingTrack

        playingTrack?.let {

            val position = it.position
            val duration = it.duration

            trackProgressBar.value = ((position / duration.toFloat()) * 100f).toInt()
            trackProgressBar.labelFormat = "${StringUtils.formatDuration(position)} / ${StringUtils.formatDuration(duration)}"
        }
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
    fun onTrackPlay(playEvent: TrackPlayEvent) {

        titleLabel.text = playEvent.track.track.info.title
        playerUpdateFuture = playerUpdateExecutor.scheduleAtFixedRate(playerUpdateTask, 0L, 1L, TimeUnit.SECONDS)
    }

    @Subscribe
    fun onPlayerPause(event: PlayerPauseEvent) {

        playerUpdateFuture?.cancel(false)
    }

    @Subscribe
    fun onPlayerResume(event: PlayerResumeEvent) {

        playerUpdateFuture = playerUpdateExecutor.scheduleAtFixedRate(playerUpdateTask, 0L, 1L, TimeUnit.SECONDS)
    }
}