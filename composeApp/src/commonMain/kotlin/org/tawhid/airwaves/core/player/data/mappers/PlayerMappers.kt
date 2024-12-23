package org.tawhid.airwaves.core.player.data.mappers

import org.tawhid.airwaves.core.player.domain.Player
import org.tawhid.airwaves.radio.domain.Radio

fun Radio.toPlayer(): Player {
    return Player(
        id = id,
        title = name,
        audioUrl = url,
        imgUrl = imgUrl
    )
}

fun Player.toRadio(): Radio {
    return Radio(
        id = id,
        name = title,
        url = audioUrl,
        imgUrl = imgUrl,
        urlResolved = null,
        homepage = null,
        tags = null,
        country = null,
        state = null,
        iso = null,
        language = null,
        codec = null,
        bitrate = null,
        hls = null,
        voteCount = null,
        clickCount = null,
        sslError = null,
        geoLat = null,
        geoLong = null,
        hasExtendedInfo = null
    )
}