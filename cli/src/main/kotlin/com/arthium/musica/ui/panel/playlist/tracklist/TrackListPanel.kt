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

    val playlistTable: TrackListTable = TrackListTable()

    init {

        addComponent(playlistTable.withBorder(Borders.singleLine()), BorderLayout.Location.CENTER)
    }
}