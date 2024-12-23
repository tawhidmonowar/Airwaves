package org.tawhid.airwaves.core.player.domain

data class Player(
    val id: String,
    val title: String,
    val description: String? = null,
    val audioUrl: String,
    val imgUrl: String?
)