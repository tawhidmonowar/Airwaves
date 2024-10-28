package org.tawhid.airwaves.data.models.radio

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RaidoResponseError(
    @SerialName("message")
    val message: String? = null,
)