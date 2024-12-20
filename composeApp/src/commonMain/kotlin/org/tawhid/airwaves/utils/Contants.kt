package org.tawhid.airwaves.utils

import airwaves.composeapp.generated.resources.Res
import airwaves.composeapp.generated.resources.dark_mode
import airwaves.composeapp.generated.resources.light_mode
import airwaves.composeapp.generated.resources.system_default
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.StringResource
import org.tawhid.airwaves.data.models.radio.RadioData

enum class Theme(val title: StringResource) {
    SYSTEM_DEFAULT(Res.string.system_default),
    LIGHT_MODE(Res.string.light_mode),
    DARK_MODE(Res.string.dark_mode)
}


enum class DeviceType {
    Mobile, Desktop
}

const val BASE_URL_RADIO = "https://de1.api.radio-browser.info/json/"
const val BASE_URL_OPEN_BOOK = "https://openlibrary.org"

const val DATA_STORE_FILE_NAME = "setting.preferences_pb"

val FadeIn = fadeIn(animationSpec = tween(220, delayMillis = 90)) +
        scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(220, delayMillis = 90)
        )

val FadeOut = fadeOut(animationSpec = tween(90))

/*
val radios: List<RadioData> = listOf(
    RadioData(
        changeuuid = "01234567-89ab-cdef-0123-456789abcdgef",
        stationuuid = "01234567-89ab-cdef-0123-456789abcdef",
        name = "Best Radio",
        url = "http://www.example.com/test.pls",
        url_resolved = "http://www.example.com/test.pls",
        homepage = "https = //www.example.com",
        favicon = "https://www.shutterstock.com/image-vector/radio-channel-logo-vector-template-260nw-2121122948.jpg",
        tags = "jazz,pop,rock,indie",
        country = "Switzerland",
        countrycode = "US",
        iso_3166_2 = "US-NY",
        state = null,
        language = "german,english",
        languagecodes = "ger,eng",
        votes = 0,
        lastchangetime = "2019-12-12 18 = 37 = 02",
        lastchangetime_iso8601 = "019-12-12T18 = 37 = 02Z",
        codec = "MP3",
        bitrate = 128,
        hls = 0,
        lastcheckok = 1,
        lastchecktime = "2020-01-09 18 = 16 = 35",
        lastchecktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastcheckoktime = "2020-01-09 18 = 16 = 35",
        lastcheckoktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastlocalchecktime = "2020-01-08 23 = 18 = 38",
        lastlocalchecktime_iso8601 = "2020-01-08T23 = 18 = 38Z",
        clicktimestamp = null,
        clicktimestamp_iso8601 = null,
        clickcount = 0,
        clicktrend = 0,
        ssl_error = 0,
        geo_lat = 1.1,
        geo_long = -2.2,
        has_extended_info = false,
        serveruuid = null
    ),
    RadioData(
        changeuuid = "01234567-89ab-cdef-0123g-456789abcdef",
        stationuuid = "01234567-89ab-cdef-0123-456789abcdef",
        name = "Best Radio",
        url = "http://www.example.com/test.pls",
        url_resolved = "http://www.example.com/test.pls",
        homepage = "https = //www.example.com",
        favicon = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/BBC_World_Service_2022_%28Boxed%29.svg/1200px-BBC_World_Service_2022_%28Boxed%29.svg.png",
        tags = "jazz,pop,rock,indie",
        country = "Switzerland",
        countrycode = "US",
        iso_3166_2 = "US-NY",
        state = null,
        language = "german,english",
        languagecodes = "ger,eng",
        votes = 0,
        lastchangetime = "2019-12-12 18 = 37 = 02",
        lastchangetime_iso8601 = "019-12-12T18 = 37 = 02Z",
        codec = "MP3",
        bitrate = 128,
        hls = 0,
        lastcheckok = 1,
        lastchecktime = "2020-01-09 18 = 16 = 35",
        lastchecktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastcheckoktime = "2020-01-09 18 = 16 = 35",
        lastcheckoktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastlocalchecktime = "2020-01-08 23 = 18 = 38",
        lastlocalchecktime_iso8601 = "2020-01-08T23 = 18 = 38Z",
        clicktimestamp = null,
        clicktimestamp_iso8601 = null,
        clickcount = 0,
        clicktrend = 0,
        ssl_error = 0,
        geo_lat = 1.1,
        geo_long = -2.2,
        has_extended_info = false,
        serveruuid = null
    ),
    RadioData(
        changeuuid = "01234567-89afb-cdef-0123-456789abcdef",
        stationuuid = "01234567-89ab-cdef-0123-456789abcdef",
        name = "Best Radio",
        url = "http://www.example.com/test.pls",
        url_resolved = "http://www.example.com/test.pls",
        homepage = "https = //www.example.com",
        favicon = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/BBC_World_Service_2022_%28Boxed%29.svg/1200px-BBC_World_Service_2022_%28Boxed%29.svg.png",
        tags = "jazz,pop,rock,indie",
        country = "Switzerland",
        countrycode = "US",
        iso_3166_2 = "US-NY",
        state = null,
        language = "german,english",
        languagecodes = "ger,eng",
        votes = 0,
        lastchangetime = "2019-12-12 18 = 37 = 02",
        lastchangetime_iso8601 = "019-12-12T18 = 37 = 02Z",
        codec = "MP3",
        bitrate = 128,
        hls = 0,
        lastcheckok = 1,
        lastchecktime = "2020-01-09 18 = 16 = 35",
        lastchecktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastcheckoktime = "2020-01-09 18 = 16 = 35",
        lastcheckoktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastlocalchecktime = "2020-01-08 23 = 18 = 38",
        lastlocalchecktime_iso8601 = "2020-01-08T23 = 18 = 38Z",
        clicktimestamp = null,
        clicktimestamp_iso8601 = null,
        clickcount = 0,
        clicktrend = 0,
        ssl_error = 0,
        geo_lat = 1.1,
        geo_long = -2.2,
        has_extended_info = false,
        serveruuid = null
    ),
    RadioData(
        changeuuid = "01234567s-89ab-cdef-0123-456789abcdef",
        stationuuid = "01234567-89ab-cdef-0123-456789abcdef",
        name = "Best Radio",
        url = "http://www.example.com/test.pls",
        url_resolved = "http://www.example.com/test.pls",
        homepage = "https = //www.example.com",
        favicon = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/BBC_World_Service_2022_%28Boxed%29.svg/1200px-BBC_World_Service_2022_%28Boxed%29.svg.png",
        tags = "jazz,pop,rock,indie",
        country = "Switzerland",
        countrycode = "US",
        iso_3166_2 = "US-NY",
        state = null,
        language = "german,english",
        languagecodes = "ger,eng",
        votes = 0,
        lastchangetime = "2019-12-12 18 = 37 = 02",
        lastchangetime_iso8601 = "019-12-12T18 = 37 = 02Z",
        codec = "MP3",
        bitrate = 128,
        hls = 0,
        lastcheckok = 1,
        lastchecktime = "2020-01-09 18 = 16 = 35",
        lastchecktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastcheckoktime = "2020-01-09 18 = 16 = 35",
        lastcheckoktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastlocalchecktime = "2020-01-08 23 = 18 = 38",
        lastlocalchecktime_iso8601 = "2020-01-08T23 = 18 = 38Z",
        clicktimestamp = null,
        clicktimestamp_iso8601 = null,
        clickcount = 0,
        clicktrend = 0,
        ssl_error = 0,
        geo_lat = 1.1,
        geo_long = -2.2,
        has_extended_info = false,
        serveruuid = null
    ),
    RadioData(
        changeuuid = "0123456a7-89ab-cdef-0123-456789abcdef",
        stationuuid = "01234567-89ab-cdef-0123-456789abcdef",
        name = "Best Radio",
        url = "http://www.example.com/test.pls",
        url_resolved = "http://www.example.com/test.pls",
        homepage = "https = //www.example.com",
        favicon = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/BBC_World_Service_2022_%28Boxed%29.svg/1200px-BBC_World_Service_2022_%28Boxed%29.svg.png",
        tags = "jazz,pop,rock,indie",
        country = "Switzerland",
        countrycode = "US",
        iso_3166_2 = "US-NY",
        state = null,
        language = "german,english",
        languagecodes = "ger,eng",
        votes = 0,
        lastchangetime = "2019-12-12 18 = 37 = 02",
        lastchangetime_iso8601 = "019-12-12T18 = 37 = 02Z",
        codec = "MP3",
        bitrate = 128,
        hls = 0,
        lastcheckok = 1,
        lastchecktime = "2020-01-09 18 = 16 = 35",
        lastchecktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastcheckoktime = "2020-01-09 18 = 16 = 35",
        lastcheckoktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastlocalchecktime = "2020-01-08 23 = 18 = 38",
        lastlocalchecktime_iso8601 = "2020-01-08T23 = 18 = 38Z",
        clicktimestamp = null,
        clicktimestamp_iso8601 = null,
        clickcount = 0,
        clicktrend = 0,
        ssl_error = 0,
        geo_lat = 1.1,
        geo_long = -2.2,
        has_extended_info = false,
        serveruuid = null
    ),
    RadioData(
        changeuuid = "01234567-89ab-cdef-0w123-456789abcdef",
        stationuuid = "01234567-89ab-cdef-0123-456789abcdef",
        name = "Best Radio",
        url = "http://www.example.com/test.pls",
        url_resolved = "http://www.example.com/test.pls",
        homepage = "https = //www.example.com",
        favicon = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/BBC_World_Service_2022_%28Boxed%29.svg/1200px-BBC_World_Service_2022_%28Boxed%29.svg.png",
        tags = "jazz,pop,rock,indie",
        country = "Switzerland",
        countrycode = "US",
        iso_3166_2 = "US-NY",
        state = null,
        language = "german,english",
        languagecodes = "ger,eng",
        votes = 0,
        lastchangetime = "2019-12-12 18 = 37 = 02",
        lastchangetime_iso8601 = "019-12-12T18 = 37 = 02Z",
        codec = "MP3",
        bitrate = 128,
        hls = 0,
        lastcheckok = 1,
        lastchecktime = "2020-01-09 18 = 16 = 35",
        lastchecktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastcheckoktime = "2020-01-09 18 = 16 = 35",
        lastcheckoktime_iso8601 = "2020-01-09T18 = 16 = 35Z",
        lastlocalchecktime = "2020-01-08 23 = 18 = 38",
        lastlocalchecktime_iso8601 = "2020-01-08T23 = 18 = 38Z",
        clicktimestamp = null,
        clicktimestamp_iso8601 = null,
        clickcount = 0,
        clicktrend = 0,
        ssl_error = 0,
        geo_lat = 1.1,
        geo_long = -2.2,
        has_extended_info = false,
        serveruuid = null
    )
)
*/


val radioGenre: List<String> = listOf(
    "pop",
    "music",
    "news",
    "rock",
    "classical",
    "talk",
    "islamic",
    "dance",
    "jazz",
    "country",
    "alternative",
    "folk",
    "information",
    "regional",
    "oldies",
    "sports",
    "electronic",
    "culture",
    "hiphop",
    "world music",
    "religious",
    "hits"
)