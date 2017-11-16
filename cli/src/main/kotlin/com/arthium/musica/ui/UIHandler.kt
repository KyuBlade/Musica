package com.arthium.musica.ui

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.DesktopAudioPlayer
import com.arthium.musica.event.ExitEvent
import com.arthium.musica.ui.window.MainWindow
import com.googlecode.lanterna.gui2.MultiWindowTextGUI
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jnativehook.GlobalScreen


class UIHandler {

    private val terminal: Terminal
    private val screen: TerminalScreen
    private val gui: MultiWindowTextGUI
    private val mainWindow: MainWindow

    init {

        terminal = DefaultTerminalFactory().createTerminal()
        screen = TerminalScreen(terminal)
        gui = MultiWindowTextGUI(screen)
        mainWindow = MainWindow()
    }

    fun run() {

        EventBus.getDefault().register(this)

        screen.startScreen()

        gui.addWindow(mainWindow)
        mainWindow.waitUntilClosed()

        screen.stopScreen()

        AudioPlayerManager.cleanup()
        DesktopAudioPlayer.cleanup()
        EventBus.getDefault().unregister(this)
        GlobalScreen.unregisterNativeHook();
    }

    @Subscribe
    fun onExit(event: ExitEvent) {

        mainWindow.close()
    }
}