package com.arthium.musica.ui.panel.playlist

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.BorderLayout
import com.googlecode.lanterna.gui2.Borders
import com.googlecode.lanterna.gui2.Component
import com.googlecode.lanterna.gui2.Panel
import com.googlecode.lanterna.gui2.table.DefaultTableCellRenderer
import com.googlecode.lanterna.gui2.table.DefaultTableRenderer
import com.googlecode.lanterna.gui2.table.Table
import com.googlecode.lanterna.gui2.table.TableCellBorderStyle


class TrackListPanel : Panel(BorderLayout()) {

    companion object {

        fun create(): Component =
                TrackListPanel().withBorder(Borders.singleLineBevel("Tracks"))
    }

    val playlistTable: TrackListTable

    init {
        layoutData = BorderLayout.Location.CENTER

        playlistTable = TrackListTable()
        playlistTable.layoutData = BorderLayout.Location.CENTER

        addComponent(playlistTable.withBorder(Borders.singleLine()))
    }

    fun addEntry(title: String, duration: String) {

        playlistTable.tableModel.addRow(title, duration)
    }

    fun clearEntries() {

        val rowCount = playlistTable.tableModel.rowCount
        for (i in rowCount - 1 downTo 0)
            playlistTable.tableModel.removeRow(i)
    }

    class TrackListTable : Table<String>("Title", "Duration") {

        init {

            renderer = TrackListTableRenderer()
            tableCellRenderer = PlaylistTableCellRenderer()
            isEscapeByArrowKey = false
        }

        class TrackListTableRenderer : DefaultTableRenderer<String>() {

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