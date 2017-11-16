package com.arthium.musica.ui.panel.search

import com.arthium.musica.audio.DesktopAudioPlayer
import com.arthium.musica.event.AddSearchEntryEvent
import com.arthium.musica.event.ClearSearchEvent
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder
import com.googlecode.lanterna.gui2.table.DefaultTableCellRenderer
import com.googlecode.lanterna.gui2.table.DefaultTableRenderer
import com.googlecode.lanterna.gui2.table.Table
import com.googlecode.lanterna.gui2.table.TableCellBorderStyle
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class SearchPanel private constructor() : Panel(BorderLayout()) {

    companion object {

        fun create(): Border =
                SearchPanel().withBorder(Borders.doubleLine("Search"))
    }

    private val searchTable: SearchListTable

    init {

        searchTable = SearchListTable()

        addComponent(searchTable.withBorder(Borders.singleLine()), BorderLayout.Location.CENTER)
        addComponent(SearchMenuPanel.create(), BorderLayout.Location.BOTTOM)
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
    fun onClearSearch(event: ClearSearchEvent) {

        searchTable.clearEntries()
    }

    @Subscribe
    fun onAddSearchEntry(event: AddSearchEntryEvent) {

        searchTable.addTrack(event.track)
    }

    class SearchListTable : Table<String>("Title", "Duration") {

        private val tracks: MutableList<AudioTrack> = mutableListOf()

        init {

            renderer = SearchListTableRenderer()
            tableCellRenderer = PlaylistTableCellRenderer()
            isEscapeByArrowKey = false

            setSelectAction {

                ActionListDialogBuilder()
                        .setTitle("Action List Dialog")
                        .setDescription("Choose an item")
                        .addAction("Play") {

                            DesktopAudioPlayer.play(tracks[selectedRow].makeClone())
                        }
                        .addAction("Add to queue") {

                        }
                        .addAction("Add to playlist") {

                        }
                        .build()
                        .showDialog(this@SearchListTable.textGUI as WindowBasedTextGUI?)
            }
        }

        fun addTrack(track: AudioTrack) {

            tracks.add(track)
            tableModel.addRow(track.info.title, track.duration.toString())
        }

        fun clearEntries() {

            tracks.clear()

            val rowCount = tableModel.rowCount
            for (i in rowCount - 1 downTo 0)
                tableModel.removeRow(i)
        }

        class SearchListTableRenderer : DefaultTableRenderer<String>() {

            init {
                setHeaderHorizontalBorderStyle(TableCellBorderStyle.EmptySpace)
                setHeaderVerticalBorderStyle(TableCellBorderStyle.SingleLine)
            }

            override fun getPreferredSize(table: Table<String>): TerminalSize {
                // Hack to fix scrollbars
                table.visibleRows = Math.max(0, table.parent.size.rows - 4)

                return super.getPreferredSize(table)
            }
        }
    }

    class PlaylistTableCellRenderer : DefaultTableCellRenderer<String>() {

        override fun getPreferredSize(table: Table<String>, cell: String, columnIndex: Int, rowIndex: Int): TerminalSize {

            return when (columnIndex) {
                0 -> TerminalSize(Math.max(0, table.size.columns - 15), 1)
                1 -> TerminalSize(10, 1)
                else -> super.getPreferredSize(table, cell, columnIndex, rowIndex)
            }
        }
    }
}