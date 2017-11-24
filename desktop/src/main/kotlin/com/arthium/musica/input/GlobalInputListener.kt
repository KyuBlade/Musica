package com.arthium.musica.input

import com.arthium.musica.audio.AudioPlayerManager
import com.arthium.musica.audio.DesktopAudioPlayer
import com.arthium.musica.audio.track.PreviewAudioTrack
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import org.jnativehook.keyboard.NativeKeyAdapter
import org.jnativehook.keyboard.NativeKeyEvent


class GlobalInputListener : NativeKeyAdapter() {

    override fun nativeKeyReleased(event: NativeKeyEvent?) {

        event?.let {

            when (it.keyCode) {

                NativeKeyEvent.VC_MEDIA_PLAY -> {

                    if (DesktopAudioPlayer.isPaused())
                        DesktopAudioPlayer.resume()
                    else
                        DesktopAudioPlayer.pause()
                }

                NativeKeyEvent.VC_MEDIA_PREVIOUS -> {

                    AudioPlayerManager.skip(false)
                }

                NativeKeyEvent.VC_MEDIA_NEXT -> {

                    AudioPlayerManager.skip(true)
                }

                NativeKeyEvent.VC_F5 -> {

                    AudioPlayerManager.playerManager.loadItem("https://soundcloud.com/vonikk/vonikk-katana",
                            object : AudioLoadResultHandler {

                                override fun loadFailed(exception: FriendlyException?) {

                                }

                                override fun trackLoaded(track: AudioTrack) {
                                    DesktopAudioPlayer.play(PreviewAudioTrack(track))
                                }

                                override fun noMatches() {
                                }

                                override fun playlistLoaded(playlist: AudioPlaylist?) {
                                }

                            })
                }
                else -> {
                }
            }
        }
    }
}