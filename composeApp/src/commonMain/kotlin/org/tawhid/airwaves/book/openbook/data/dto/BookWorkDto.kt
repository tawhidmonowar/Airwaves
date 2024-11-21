package org.tawhid.airwaves.book.openbook.data.dto

import kotlinx.serialization.Serializable

@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
    val description: String? = null
)