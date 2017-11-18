package com.arthium.musica.ui.panel.playlist.tracklist

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.table.DefaultTableCellRenderer
import com.googlecode.lanterna.gui2.table.Table


class TrackListTableCellRenderer : DefaultTableCellRenderer<String>() {

    override fun getPreferredSize(table: Table<String>, cell: String, columnIndex: Int, rowIndex: Int): TerminalSize =
            when (columnIndex) {
                0 -> TerminalSize(Math.max(0, table.size.columns - 15), 1)
                1 -> TerminalSize(10, 1)
                else -> super.getPreferredSize(table, cell, columnIndex, rowIndex)
            }
}