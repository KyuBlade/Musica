package com.arthium.musica.ui.window

import com.arthium.musica.event.InputEvent
import com.arthium.musica.event.ShowPlaylistEvent
import com.arthium.musica.event.ShowSearchEvent
import com.arthium.musica.ui.panel.PlayerPanel
import com.arthium.musica.ui.panel.playlist.PlaylistPanel
import com.arthium.musica.ui.panel.scheduler.TrackSchedulerPanel
import com.arthium.musica.ui.panel.search.SearchPanel
import com.googlecode.lanterna.gui2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainWindow : BasicWindow() {

    private val mainPanel: Panel

    private val topPanel: Panel

    private val playlistPanel: PlaylistPanel
    private val playlistPanelContainer: Border

    private val searchPanel: SearchPanel
    private val searchPanelContainer: Border

    init {
        setHints(
                listOf(
                        Window.Hint.FULL_SCREEN, Window.Hint.NO_POST_RENDERING
                )
        )

        mainPanel = Panel(BorderLayout())

        topPanel = Panel(BorderLayout())
        topPanel.addComponent(PlayerPanel.create(), BorderLayout.Location.LEFT)
        topPanel.addComponent(TrackSchedulerPanel.create(), BorderLayout.Location.CENTER)

        mainPanel.addComponent(topPanel, BorderLayout.Location.TOP)

        playlistPanelContainer = PlaylistPanel.create()
        playlistPanel = playlistPanelContainer.component as PlaylistPanel

        mainPanel.addComponent(playlistPanelContainer, BorderLayout.Location.CENTER)

        searchPanelContainer = SearchPanel.create()
        searchPanel = searchPanelContainer.component as SearchPanel

        component = mainPanel
    }

    override fun setTextGUI(textGUI: WindowBasedTextGUI?) {
        super.setTextGUI(textGUI)

        if (textGUI != null) {
            EventBus.getDefault().register(this)
        } else {
            EventBus.getDefault().unregister(this)
        }

        textGUI?.addListener({ _, keyStroke ->

            EventBus.getDefault().post(InputEvent(keyStroke))
            false
        })
    }

    @Subscribe
    fun onShowPlaylist(event: ShowPlaylistEvent) {

        mainPanel.removeComponent(searchPanelContainer)
        mainPanel.addComponent(playlistPanelContainer, BorderLayout.Location.CENTER)
    }

    @Subscribe
    fun onShowSearch(event: ShowSearchEvent) {

        mainPanel.removeComponent(playlistPanelContainer)
        mainPanel.addComponent(searchPanelContainer, BorderLayout.Location.CENTER)
    }
}