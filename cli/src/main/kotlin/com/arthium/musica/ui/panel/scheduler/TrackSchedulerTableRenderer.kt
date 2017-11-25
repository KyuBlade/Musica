package com.arthium.musica.ui.panel.scheduler

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.table.DefaultTableRenderer
import com.googlecode.lanterna.gui2.table.Table


class TrackSchedulerTableRenderer : DefaultTableRenderer<String>() {

    override fun getPreferredSize(table: Table<String>): TerminalSize {
        // Hack to fix scrollbars
        table.visibleRows = Math.min(4, table.tableModel.rowCount)

        return super.getPreferredSize(table)
    }
}