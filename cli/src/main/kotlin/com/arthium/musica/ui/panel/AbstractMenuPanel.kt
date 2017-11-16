package com.arthium.musica.ui.panel

import com.arthium.musica.event.InputEvent
import com.googlecode.lanterna.gui2.Container
import com.googlecode.lanterna.gui2.GridLayout
import com.googlecode.lanterna.gui2.Label
import com.googlecode.lanterna.gui2.Panel
import com.googlecode.lanterna.input.KeyStroke
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


abstract class AbstractMenuPanel(numberOfColumns: Int) : Panel(GridLayout(numberOfColumns).setHorizontalSpacing(5)) {


    protected fun addText(text: String) {

        addComponent(Label(text)
                .setLayoutData(
                        GridLayout.createLayoutData(
                                GridLayout.Alignment.BEGINNING,
                                GridLayout.Alignment.BEGINNING,
                                false,
                                false,
                                1,
                                1
                        )
                )
        )
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
    fun onInput(event: InputEvent) {

        if(textGUI != null)
            onKeyPressed(event.keyStroke)
    }

    protected abstract fun onKeyPressed(keyStroke: KeyStroke)
}