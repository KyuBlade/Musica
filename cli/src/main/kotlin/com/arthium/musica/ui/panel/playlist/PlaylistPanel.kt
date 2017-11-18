package com.arthium.musica.ui.panel.playlist

import com.arthium.musica.ui.panel.playlist.tracklist.TrackListPanel
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.*


class PlaylistPanel : Panel(BorderLayout()) {

    companion object {

        fun create(): Border =
                PlaylistPanel().withBorder(Borders.doubleLine("Playlist"))
    }

    val trackListPanel: TrackListPanel

    init {
        val playlistListBox = ActionListBox(TerminalSize(15, 20))
                .setLayoutData(BorderLayout.Location.LEFT)

        (0..20).forEach {
            playlistListBox.addItem("Playlist $it", {
                println("Display playlist $it")
            })
        }

        addComponent(playlistListBox.withBorder(Borders.singleLineBevel("Playlists")))

        val trackListPanelContainer: Border = TrackListPanel.create() as Border
        trackListPanel = trackListPanelContainer.component as TrackListPanel
        addComponent(trackListPanelContainer)

        val menuPanel = PlaylistMenuPanel.create().setLayoutData(BorderLayout.Location.BOTTOM)
        addComponent(menuPanel)
    }
}