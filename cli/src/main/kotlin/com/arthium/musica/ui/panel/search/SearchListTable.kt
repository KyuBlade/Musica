package com.arthium.musica.ui.panel.search

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.DesktopAudioPlayer
import com.arthium.musica.utils.StringUtils
import com.googlecode.lanterna.gui2.WindowBasedTextGUI
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder
import com.googlecode.lanterna.gui2.table.Table
import com.sedmelluq.discord.lavaplayer.track.AudioTrack


class SearchListTable : Table<String>("Title", "Duration") {

    private val tracks: MutableList<AudioTrack> = mutableListOf()

    init {

        renderer = SearchListTableRenderer()
        tableHeaderRenderer = SearchListTableHeaderRenderer()
        tableCellRenderer = SearchListTableCellRenderer()
        isEscapeByArrowKey = false

        setSelectAction {

            ActionListDialogBuilder()
                    .setTitle("Action List Dialog")
                    .setDescription("Choose an item")
                    .addAction("Play") {

                        DesktopAudioPlayer.play(tracks[selectedRow])
                    }
                    .addAction("Add to scheduler") {
                        AudioPlayerManager.trackScheduler.add(tracks[selectedRow])
                    }
                    .addAction("Add to playlist") {

                    }
                    .build()
                    .showDialog(textGUI as WindowBasedTextGUI?)
        }
    }

    fun addTrack(track: AudioTrack) {

        tracks.add(track)
        tableModel.addRow(track.info.title, StringUtils.formatDuration(track.duration))
    }

    fun clearEntries() {

        tracks.clear()

        val rowCount = tableModel.rowCount
        for (i in rowCount - 1 downTo 0)
            tableModel.removeRow(i)
    }
}