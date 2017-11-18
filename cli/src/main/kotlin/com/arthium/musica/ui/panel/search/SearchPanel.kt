package com.arthium.musica.ui.panel.search

import com.arthium.musica.event.AddSearchEntryEvent
import com.arthium.musica.event.ClearSearchEvent
import com.googlecode.lanterna.gui2.*
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
}