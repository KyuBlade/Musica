package com.arthium.musica.ui.panel.playlist.tracklist

import com.arthium.musica.utils.StringUtils
import com.googlecode.lanterna.gui2.table.Table
import com.sedmelluq.discord.lavaplayer.track.AudioTrack

class TrackListTable : Table<String>("Title", "Duration") {

    init {

        renderer = TrackListTableRenderer()
        tableHeaderRenderer = TrackListTableHeaderRenderer()
        tableCellRenderer = TrackListTableCellRenderer()
        isEscapeByArrowKey = false
    }

    fun addTrack(track: AudioTrack) {

        tableModel.addRow(track.info.title, StringUtils.formatDuration(track.duration))
    }

    fun clearEntries() {

        val rowCount = tableModel.rowCount
        for (i in rowCount - 1 downTo 0)
            tableModel.removeRow(i)
    }
}