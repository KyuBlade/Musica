package com.arthium.musica.ui.panel.playlist

import com.arthium.musica.audio.playlist.Playlist
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.ActionListBox
import com.googlecode.lanterna.gui2.Border
import com.googlecode.lanterna.gui2.Borders


class PlaylistActionListBox private constructor() : ActionListBox(TerminalSize(15, 20)) {

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
}