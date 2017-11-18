package com.arthium.musica.ui.panel.scheduler

import com.arthium.musica.ui.NullTableHeaderRenderer
import com.googlecode.lanterna.gui2.table.Table


class TrackSchedulerTable : Table<String>("", "", "") {

    init {

        renderer = TrackSchedulerTableRenderer()
        tableCellRenderer = TrackSchedulerTableCellRenderer()
        tableHeaderRenderer = NullTableHeaderRenderer()
        isEscapeByArrowKey = false
    }
}