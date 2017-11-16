package com.arthium.musica.ui.panel.search

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.SearchResultHandler
import com.arthium.musica.event.ClearSearchEvent
import com.arthium.musica.event.ShowPlaylistEvent
import com.arthium.musica.ui.panel.AbstractMenuPanel
import com.googlecode.lanterna.gui2.Border
import com.googlecode.lanterna.gui2.Borders
import com.googlecode.lanterna.gui2.WindowBasedTextGUI
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import org.greenrobot.eventbus.EventBus


class SearchMenuPanel private constructor() : AbstractMenuPanel(3) {

    companion object {

        fun create(): Border =
                SearchMenuPanel().withBorder(Borders.doubleLineBevel())
    }

    init {
        addText("F1 - Playlists")
        addText("s - Search")
        addText("enter - Actions")
    }

    override fun onKeyPressed(keyStroke: KeyStroke) {

        when (keyStroke.keyType) {

            KeyType.F1 -> EventBus.getDefault().post(ShowPlaylistEvent())
            KeyType.Character -> {

                if (keyStroke.character == 's') {

                    val keywords: String = TextInputDialogBuilder()
                            .setTitle("Tracks search")
                            .setDescription("Enter the keywords to search for")
                            .build()
                            .showDialog(textGUI as WindowBasedTextGUI?)

                    EventBus.getDefault().post(ClearSearchEvent())
                    AudioPlayerManager.search(keywords, SearchResultHandler())
                }
            }
            else -> {
            }
        }
    }
}