package org.tawhid.airwaves.data.api

import kotlinx.serialization.Serializable

@Serializable
internal data class PodcastData(
    val id : Int,
    val title: String,
    val url: String,
    val description: String,
    val image: String
)
