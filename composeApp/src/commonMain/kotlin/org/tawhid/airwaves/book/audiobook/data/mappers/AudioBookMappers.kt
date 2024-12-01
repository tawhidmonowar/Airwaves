package org.tawhid.airwaves.book.audiobook.data.mappers

import org.tawhid.airwaves.book.audiobook.data.dto.AudioBookTrackDto
import org.tawhid.airwaves.book.audiobook.data.dto.SearchedAudioBookDto
import org.tawhid.airwaves.book.audiobook.domain.AudioBook
import org.tawhid.airwaves.book.audiobook.domain.AudioBookTracks

fun SearchedAudioBookDto.toAudioBook(): AudioBook {
    val coverImg = zipFileUrl?.substringAfter("download/")?.substringBeforeLast("/")
    return AudioBook(
        id = id,
        title = title,
        description = description,
        authors = authors?.map { it.firstName + " " + it.lastName } ?: emptyList(),
        language = language,
        copyrightYear = copyrightYear,
        numSections = numSections,
        textSourceUrl = textSourceUrl,
        zipFileUrl = zipFileUrl,
        libriVoxUrl = libriVoxUrl,
        totalTime = totalTime,
        imgUrl = "https://archive.org/services/img/${coverImg}"
    )
}

fun AudioBookTrackDto.toAudioBookTracks(): AudioBookTracks {
    return AudioBookTracks(
        id = id,
        sectionNumber = sectionNumber,
        title = title,
        listenUrl = listenUrl,
        language = language,
        playtime = playtime,
    )
}