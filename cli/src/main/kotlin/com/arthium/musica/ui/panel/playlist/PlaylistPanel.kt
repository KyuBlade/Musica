package com.arthium.musica.ui.panel.playlist

import com.arthium.musica.audio.playlist.Playlist
import com.arthium.musica.audio.playlist.PlaylistManager
import com.arthium.musica.event.PlaylistCreatedEvent
import com.arthium.musica.ui.panel.playlist.tracklist.TrackListPanel
import com.googlecode.lanterna.gui2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class PlaylistPanel : Panel(BorderLayout()) {

    companion object {

        fun create(): Border =
                PlaylistPanel().withBorder(Borders.doubleLine("Playlist"))
    }

    private val playlistListBox: PlaylistActionListBox
    private val trackListPanel: TrackListPanel

    init {
        val playlistListBoxComponent = PlaylistActionListBox.create()
        playlistListBox = playlistListBoxComponent.component as PlaylistActionListBox

        addComponent(playlistListBoxComponent, BorderLayout.Location.LEFT)

        val trackListPanelContainer: Border = TrackListPanel.create() as Border
        trackListPanel = trackListPanelContainer.component as TrackListPanel
        addComponent(trackListPanelContainer, BorderLayout.Location.CENTER)

        val menuPanel = PlaylistMenuPanel.create().setLayoutData(BorderLayout.Location.BOTTOM)
        addComponent(menuPanel)

        // Set listener for playlist changes
        playlistListBox.listener = object : ActionListBoxChangeListener {

            override fun onSelectionChange(selectedIndex: Int, selectedItem: Runnable) {

                val selectedPlaylist: Playlist? = PlaylistManager.get(selectedItem.toString())
                selectedPlaylist?.let { trackListPanel.playlistTable.setPlaylist(it) }
            }
        }

        // Populate list
        PlaylistManager.get().forEach { playlist ->

            playlistListBox.add(playlist)
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
    fun onPlaylistCreated(event: PlaylistCreatedEvent) {

        playlistListBox.add(event.playlist)
    }
}