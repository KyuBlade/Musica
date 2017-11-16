package com.arthium.musica

import com.arthium.musica.input.GlobalInputListener
import com.arthium.musica.ui.UIHandler
import org.jnativehook.GlobalScreen


fun main(args: Array<String>) {

    object : DesktopLauncher() {

        override fun init() {

            GlobalScreen.addNativeKeyListener(GlobalInputListener())

            UIHandler().run()
        }
    }
}