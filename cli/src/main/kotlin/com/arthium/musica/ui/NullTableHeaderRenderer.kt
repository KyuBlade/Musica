package com.arthium.musica.ui

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.TextGUIGraphics
import com.googlecode.lanterna.gui2.table.Table
import com.googlecode.lanterna.gui2.table.TableHeaderRenderer


class NullTableHeaderRenderer : TableHeaderRenderer<String> {

    override fun getPreferredSize(table: Table<String>?, label: String?, columnIndex: Int): TerminalSize =
            TerminalSize.ZERO

    override fun drawHeader(table: Table<String>?, label: String?, index: Int, textGUIGraphics: TextGUIGraphics?) {
    }
}