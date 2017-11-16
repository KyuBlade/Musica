package com.arthium.musica.ui.panel.playlist

import com.arthium.musica.event.ExitEvent
import com.arthium.musica.event.ShowSearchEvent
import com.arthium.musica.ui.panel.AbstractMenuPanel
import com.googlecode.lanterna.gui2.BorderLayout
import com.googlecode.lanterna.gui2.Borders
import com.googlecode.lanterna.gui2.Component
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import org.greenrobot.eventbus.EventBus


class PlaylistMenuPanel : AbstractMenuPanel(3) {

    companion object {

        fun create(): Component =
                PlaylistMenuPanel().withBorder(Borders.doubleLineBevel())
    }

    init {
        layoutData = BorderLayout.Location.BOTTOM

        addText("F1 - Search")
        addText("F2 - Options")
        addText("q - Exit")
    }

    override fun onKeyPressed(keyStroke: KeyStroke) {

        when (keyStroke.keyType) {

            KeyType.F1 -> {

                EventBus.getDefault().post(ShowSearchEvent())
            }
            KeyType.Character -> {

                if (keyStroke.character == 'q') {
                    EventBus.getDefault().post(ExitEvent())
                }
            }
            else -> {
            }
        }
    }
}