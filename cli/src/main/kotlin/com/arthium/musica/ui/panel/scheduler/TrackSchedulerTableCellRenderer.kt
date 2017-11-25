package com.arthium.musica.ui.panel.scheduler

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.TextGUIGraphics
import com.googlecode.lanterna.gui2.table.DefaultTableCellRenderer
import com.googlecode.lanterna.gui2.table.Table


class TrackSchedulerTableCellRenderer : DefaultTableCellRenderer<String>() {

    override fun getPreferredSize(table: Table<String>, cell: String, columnIndex: Int, rowIndex: Int): TerminalSize =
            when (columnIndex) {
                0 -> TerminalSize(3, 1)
                1 -> TerminalSize(Math.max(0, table.size.columns - 15), 1)
                2 -> TerminalSize(10, 1)
                else -> super.getPreferredSize(table, cell, columnIndex, rowIndex)
            }

    override fun drawCell(table: Table<String>, cell: String, columnIndex: Int, rowIndex: Int, textGUIGraphics: TextGUIGraphics) {

        when (columnIndex) {

            0 -> {
                val themeDefinition = table.themeDefinition
                if (table.selectedColumn == columnIndex && table.selectedRow == rowIndex ||
                        table.selectedRow == rowIndex && !table.isCellSelection) {

                    if (table.isFocused) {

                        textGUIGraphics.applyThemeStyle(themeDefinition.active)
                    } else {

                        textGUIGraphics.applyThemeStyle(themeDefinition.selected)
                    }

                    textGUIGraphics.fill(' ')  //Make sure to fill the whole cell first
                } else {

                    textGUIGraphics.applyThemeStyle(themeDefinition.normal)
                }

                textGUIGraphics.putString(0, 0, (rowIndex + 1).toString())

            }
            else -> super.drawCell(table, cell, columnIndex, rowIndex, textGUIGraphics)
        }
    }
}