package com.arthium.musica

import com.arthium.musica.audio.playlist.PlaylistManager
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import java.util.logging.Level
import java.util.logging.Logger


abstract class DesktopLauncher {

    init {
        try {
            PlaylistManager.load()

            GlobalScreen.registerNativeHook()

            val logger: Logger = Logger.getLogger(GlobalScreen::class.java.`package`.name)
            logger.level = Level.WARNING
            logger.useParentHandlers = false

        } catch (ex: NativeHookException) {
            println("There was a problem registering the native hook.")
            System.err.println(ex.message)
        }

        init()
    }

    abstract fun init()
}