package com.arthium.musica.ui.panel.playlist.tracklist

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.playlist.Playlist
import com.arthium.musica.utils.StringUtils
import com.googlecode.lanterna.gui2.WindowBasedTextGUI
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder
import com.googlecode.lanterna.gui2.table.Table
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TrackListTable : Table<String>("Title", "Duration") {

    private val LOGGER: Logger = LoggerFactory.getLogger(javaClass)

    private lateinit var playlist: Playlist

    init {

        renderer = TrackListTableRenderer()
        tableHeaderRenderer = TrackListTableHeaderRenderer()
        tableCellRenderer = TrackListTableCellRenderer()
        isEscapeByArrowKey = false

        setSelectAction {

            ActionListDialogBuilder()
                    .addAction("Add playlist to scheduler", {

                        AudioPlayerManager.trackScheduler.add(playlist)
                    })
                    .addAction("Play playlist", {

                        AudioPlayerManager.trackScheduler.set(playlist)
                    })
                    .build()
                    .showDialog(textGUI as WindowBasedTextGUI?)
        }
    }

    fun setPlaylist(playlist: Playlist) {

        this.playlist = playlist

        clearEntries()
        playlist.tracks.forEach { addEntry(it.title, it.duration) }
    }

    fun addEntry(title: String, duration: Long) {

        tableModel.addRow(title, StringUtils.formatDuration(duration))
    }

    fun clearEntries() {

        val rowCount = tableModel.rowCount
        for (i in rowCount - 1 downTo 0)
            tableModel.removeRow(i)
    }
}