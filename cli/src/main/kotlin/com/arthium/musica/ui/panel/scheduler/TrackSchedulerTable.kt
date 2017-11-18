package com.arthium.musica.ui.panel.scheduler

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.DesktopAudioPlayer
import com.arthium.musica.ui.NullTableHeaderRenderer
import com.arthium.musica.utils.StringUtils
import com.googlecode.lanterna.gui2.table.Table
import com.sedmelluq.discord.lavaplayer.track.AudioTrack


class TrackSchedulerTable : Table<String>("", "", "") {

    init {

        renderer = TrackSchedulerTableRenderer()
        tableCellRenderer = TrackSchedulerTableCellRenderer()
        tableHeaderRenderer = NullTableHeaderRenderer()
        isEscapeByArrowKey = false
        setSelectAction {

            val track = AudioPlayerManager.trackScheduler.get(selectedRow)
            DesktopAudioPlayer.play(track.makeClone())
        }
    }

    fun addTrack(track: AudioTrack) {

        tableModel.addRow(
                (tableModel.rowCount + 1).toString(),
                track.info.title,
                StringUtils.formatDuration(track.duration)
        )
    }

    fun removeAt(index: Int) {

        tableModel.removeRow(index)
    }
}