package org.tawhid.airwaves.book.openbook.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingResponseDto(
    @SerialName("works") val results: List<SearchedBookDto>
)
