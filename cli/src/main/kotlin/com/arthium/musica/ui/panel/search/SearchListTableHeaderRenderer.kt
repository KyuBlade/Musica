package com.arthium.musica.ui.panel.search

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.table.DefaultTableHeaderRenderer
import com.googlecode.lanterna.gui2.table.Table


class SearchListTableHeaderRenderer : DefaultTableHeaderRenderer<String>() {

    override fun getPreferredSize(table: Table<String>, label: String?, columnIndex: Int): TerminalSize =
            when (columnIndex) {
                0 -> TerminalSize(Math.max(0, table.size.columns - 15), 1)
                1 -> TerminalSize(10, 1)
                else -> super.getPreferredSize(table, label, columnIndex)
            }
}