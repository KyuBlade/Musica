package com.arthium.musica.ui.panel.playlist.tracklist

import com.googlecode.lanterna.gui2.BorderLayout
import com.googlecode.lanterna.gui2.Borders
import com.googlecode.lanterna.gui2.Component
import com.googlecode.lanterna.gui2.Panel


class TrackListPanel : Panel(BorderLayout()) {

    companion object {

        fun create(): Component =
                TrackListPanel().withBorder(Borders.singleLineBevel("Tracks"))
    }

    val playlistTable: TrackListTable

    init {
        layoutData = BorderLayout.Location.CENTER

        playlistTable = TrackListTable()
        playlistTable.layoutData = BorderLayout.Location.CENTER

        addComponent(playlistTable.withBorder(Borders.singleLine()))
    }

    fun addEntry(title: String, duration: String) {

        playlistTable.tableModel.addRow(title, duration)
    }

    fun clearEntries() {

        val rowCount = playlistTable.tableModel.rowCount
        for (i in rowCount - 1 downTo 0)
            playlistTable.tableModel.removeRow(i)
    }
}