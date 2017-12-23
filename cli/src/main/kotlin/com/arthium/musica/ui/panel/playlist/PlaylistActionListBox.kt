package com.arthium.musica.ui.panel.playlist

import com.arthium.musica.audio.playlist.Playlist
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.ActionListBox
import com.googlecode.lanterna.gui2.Border
import com.googlecode.lanterna.gui2.Borders
import com.googlecode.lanterna.gui2.Interactable
import com.googlecode.lanterna.input.KeyStroke


class PlaylistActionListBox private constructor() : ActionListBox(TerminalSize(15, 20)) {

    private var lastSelected: Int = -1
    var listener: ActionListBoxChangeListener? = null

    companion object {

        fun create(): Border =
                PlaylistActionListBox().withBorder(Borders.singleLineBevel("Playlists"))
    }

    init {

    }

    fun add(playlist: Playlist) {

        addItem(playlist.name, {

            println("Play playlist ${playlist.name}")
        })
    }

    override fun handleKeyStroke(keyStroke: KeyStroke?): Interactable.Result {

        val result = super.handleKeyStroke(keyStroke)
        if (selectedIndex != lastSelected) {

            lastSelected = selectedIndex
            listener?.onSelectionChange(selectedIndex, selectedItem)
        }

        return result
    }
}