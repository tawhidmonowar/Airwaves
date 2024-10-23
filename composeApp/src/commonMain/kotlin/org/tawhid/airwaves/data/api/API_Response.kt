package org.tawhid.airwaves.data.api

import kotlinx.serialization.Serializable

@Serializable
internal data class API_Response(

    val result : List<PodcastData>
)
