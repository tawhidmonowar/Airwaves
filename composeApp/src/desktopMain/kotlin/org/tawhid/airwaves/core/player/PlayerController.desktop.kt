package org.tawhid.airwaves.core.player

import uk.co.caprica.vlcj.media.Media
import uk.co.caprica.vlcj.media.MediaEventAdapter
import uk.co.caprica.vlcj.media.MediaParsedStatus
import uk.co.caprica.vlcj.media.Meta
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent

actual class PlayerController {

    private val player = AudioPlayerComponent()
    private val options = arrayOf(
        ":network-caching=1000",
        ":https-user-agent=Mozilla/5.0"
    )

    actual fun play(audioUrl: String) {
        player.mediaPlayer().media().startPaused(audioUrl ,*options)
        player.mediaPlayer().media().parsing().parse();
        player.mediaPlayer().controls().play()

        player.mediaPlayer().events().addMediaEventListener(object : MediaEventAdapter() {
            override fun mediaParsedChanged(media: Media?, newStatus: MediaParsedStatus?) {
                super.mediaParsedChanged(media, newStatus)
            }

            override fun mediaMetaChanged(media: Media?, metaType: Meta?) {

                val nowPlaying = player.mediaPlayer().media().meta().get(Meta.NOW_PLAYING)
                println("Now Playing: $nowPlaying")

                val title = player.mediaPlayer().media().meta().get(Meta.TITLE)
                println("Title: $title")

                val dec = player.mediaPlayer().media().meta().get(Meta.GENRE)
                println("Dec: $dec")

                super.mediaMetaChanged(media, metaType)
            }

        })

    }

    actual fun pause() {
        if (player.mediaPlayer().status().isPlaying) {
            player.mediaPlayer().controls().pause()
        }
    }
}