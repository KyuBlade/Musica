package com.arthium.musica.ui.panel.scheduler

import com.googlecode.lanterna.gui2.Border
import com.googlecode.lanterna.gui2.BorderLayout
import com.googlecode.lanterna.gui2.Borders
import com.googlecode.lanterna.gui2.Panel
import com.googlecode.lanterna.gui2.table.Table


class TrackSchedulerPanel private constructor() : Panel(BorderLayout()) {

    companion object {

        fun create(): Border =
                TrackSchedulerPanel().withBorder(Borders.singleLine("Scheduler"))
    }

    private val schedulerTable: Table<String>

    init {

        schedulerTable = TrackSchedulerTable()


        schedulerTable.tableModel.addRow("1. ", "Title", "Duration")
        schedulerTable.tableModel.addRow("2. ", "Title", "Duration")
        schedulerTable.tableModel.addRow("3. ", "Title", "Duration")

        addComponent(schedulerTable, BorderLayout.Location.CENTER)
    }
}