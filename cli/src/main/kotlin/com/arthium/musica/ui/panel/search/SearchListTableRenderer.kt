package com.arthium.musica.ui.panel.search

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.table.DefaultTableRenderer
import com.googlecode.lanterna.gui2.table.Table
import com.googlecode.lanterna.gui2.table.TableCellBorderStyle


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