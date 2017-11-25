package com.arthium.musica.ui.panel.scheduler

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.DesktopAudioPlayer
import com.arthium.musica.ui.NullTableHeaderRenderer
import com.arthium.musica.utils.StringUtils
import com.googlecode.lanterna.gui2.WindowBasedTextGUI
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder
import com.googlecode.lanterna.gui2.table.Table
import com.sedmelluq.discord.lavaplayer.track.AudioTrack


class TrackSchedulerTable : Table<String>("", "", "") {

    init {

        renderer = TrackSchedulerTableRenderer()
        tableCellRenderer = TrackSchedulerTableCellRenderer()
        tableHeaderRenderer = NullTableHeaderRenderer()
        isEscapeByArrowKey = false

        setSelectAction {

            if (tableModel.rowCount == 0)
                return@setSelectAction


            ActionListDialogBuilder()
                    .setTitle("Actions")
                    .addAction("Play") {

                        val track = AudioPlayerManager.trackScheduler.get(selectedRow)
                        track?.let { DesktopAudioPlayer.play(it) }

                    }
                    .addAction("Remove") {

                        AudioPlayerManager.trackScheduler.remove(selectedRow)
                    }
                    .build()
                    .showDialog(textGUI as WindowBasedTextGUI)
        }
    }

    fun addTrack(track: AudioTrack) {

        tableModel.addRow(
                "",
                track.info.title,
                StringUtils.formatDuration(track.duration)
        )
    }

    fun removeAt(index: Int) {

        if (selectedRow == index) {

            val rowCount = tableModel.rowCount - 1
            var nextIndex = index

            if (nextIndex >= rowCount)
                nextIndex = Math.max(index - 1, 0)
            else if (nextIndex < 0)
                nextIndex = 0

            selectedRow = nextIndex
        }

        tableModel.removeRow(index)
    }
}