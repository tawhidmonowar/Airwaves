package org.tawhid.airwaves

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform