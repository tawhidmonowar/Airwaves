package org.tawhid.airwaves.player

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import uk.co.caprica.vlcj.media.Media
import uk.co.caprica.vlcj.media.MediaEventAdapter
import uk.co.caprica.vlcj.media.MediaParsedStatus
import uk.co.caprica.vlcj.media.MediaRef
import uk.co.caprica.vlcj.media.Meta
import uk.co.caprica.vlcj.media.TrackType
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent

actual class PlayerController {

    private val audioPlayerComponent = AudioPlayerComponent()

    actual fun prepare(pathSource: String) {

        audioPlayerComponent.mediaPlayer().media().startPaused(pathSource)
        audioPlayerComponent.mediaPlayer().media().parsing().parse();
        audioPlayerComponent.mediaPlayer().controls().play()



        audioPlayerComponent.mediaPlayer().events()
            .addMediaEventListener(object : MediaEventAdapter() {
                override fun mediaParsedChanged(media: Media?, newStatus: MediaParsedStatus?) {

                    val mediaPlayer = audioPlayerComponent.mediaPlayer();
                    val audioTracks = mediaPlayer.media().meta().get(Meta.NOW_PLAYING)
                    val titleCount = mediaPlayer.media().info().textTracks()

                    println("Media Parsed: $titleCount")
                    super.mediaParsedChanged(media, newStatus)
                }

                override fun mediaMetaChanged(media: Media?, metaType: Meta?) {

                    val nowPlaying = audioPlayerComponent.mediaPlayer().media().meta().get(Meta.NOW_PLAYING)
                    println("Now Playing: $nowPlaying")

                    val title = audioPlayerComponent.mediaPlayer().media().meta().get(Meta.TITLE)
                    println("Title: $title")

                    val dec = audioPlayerComponent.mediaPlayer().media().meta().get(Meta.DESCRIPTION)
                    println("Now Playing: $dec")

                    super.mediaMetaChanged(media, metaType)
                }

            })


    }

    actual fun start() {
    }

    actual fun pause() {
    }

    actual fun stop() {
    }

    actual fun release() {
    }

    actual fun isPlaying(): Boolean {
        return true
    }

    @Composable
    actual fun playCompose(audioUrl: String) {
        prepare(audioUrl)
    }
}