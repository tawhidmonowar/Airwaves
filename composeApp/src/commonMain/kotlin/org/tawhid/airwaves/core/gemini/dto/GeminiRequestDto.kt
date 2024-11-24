package org.tawhid.airwaves.core.gemini.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContentItem(val parts: List<RequestPart>)

@Serializable
data class RequestBody(val contents: List<ContentItem>)

@Serializable
data class RequestPart(
    val text: String? = null,
)