package com.arthium.musica.ui.panel.playlist.tracklist

import com.googlecode.lanterna.gui2.table.Table


class TrackListTable : Table<String>("Title", "Duration") {

    init {

        renderer = TrackListTableRenderer()
        tableCellRenderer = TrackListTableCellRenderer()
        isEscapeByArrowKey = false
    }
}