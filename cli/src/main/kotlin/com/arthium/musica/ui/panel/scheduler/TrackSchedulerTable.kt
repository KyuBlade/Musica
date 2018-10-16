package com.arthium.musica.ui.panel.scheduler

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.DesktopAudioPlayer
import com.arthium.musica.audio.playlist.Playlist
import com.arthium.musica.audio.playlist.PlaylistManager
import com.arthium.musica.audio.playlist.PlaylistTrack
import com.arthium.musica.ui.NullTableHeaderRenderer
import com.arthium.musica.utils.StringUtils
import com.googlecode.lanterna.gui2.WindowBasedTextGUI
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder
import com.googlecode.lanterna.gui2.table.Table
import com.sedmelluq.discord.lavaplayer.track.AudioTrack


class TrackSchedulerTable : Table<String>("", "", "") {

    init {

        renderer = TrackSchedulerTableRenderer()
        tableCellRenderer = TrackSchedulerTableCellRenderer()
        tableHeaderRenderer = NullTableHeaderRenderer()
        isEscapeByArrowKey = false

        setSelectAction {

            if (tableModel.rowCount == 0)
                return@setSelectAction


            ActionListDialogBuilder()
                    .setTitle("Actions")
                    .addAction("Play") {

                        val track = AudioPlayerManager.trackScheduler.get(selectedRow)
                        track?.let { DesktopAudioPlayer.play(it) }

                    }
                    .addAction("Remove") {

                        AudioPlayerManager.trackScheduler.remove(selectedRow)
                    }
                    .addAction("Create playlist from scheduling ...") {

                        val playlistName = TextInputDialogBuilder()
                                .setValidator { content: String ->

                                    when {
                                        content.isBlank() -> "Playlist name must not be empty"
                                        PlaylistManager.exist(content) -> "Playlist $content already exist"
                                        else -> null
                                    }
                                }
                                .build()
                                .showDialog(textGUI as WindowBasedTextGUI?)
                                ?.let { it ->

                                    val playlist = Playlist(it)
                                    AudioPlayerManager.trackScheduler.get().forEach {

                                        playlist.tracks.add(PlaylistTrack(it.track))
                                    }

                                    PlaylistManager.add(playlist)
                                }
                    }
                    .build()
                    .showDialog(textGUI as WindowBasedTextGUI)
        }
    }

    fun addTrack(track: AudioTrack) {

        tableModel.addRow(
                "",
                track.info.title,
                StringUtils.formatDuration(track.duration)
        )
    }

    fun removeAt(index: Int) {

        if (selectedRow == index) {

            val rowCount = tableModel.rowCount - 1
            var nextIndex = index

            if (nextIndex >= rowCount)
                nextIndex = Math.max(index - 1, 0)
            else if (nextIndex < 0)
                nextIndex = 0

            selectedRow = nextIndex
        }

        tableModel.removeRow(index)
    }

    fun clearEntries() {

        val rowCount = tableModel.rowCount
        for (i in rowCount - 1 downTo 0)
            tableModel.removeRow(i)
    }
}