package org.tawhid.airwaves.book.openbook.data.mappers

import org.tawhid.airwaves.book.openbook.data.database.BookEntity
import org.tawhid.airwaves.book.openbook.data.dto.SearchedBookDto
import org.tawhid.airwaves.book.openbook.domain.Book


fun SearchedBookDto.toBook(): Book {
    return Book (
        id = id.substringAfterLast("/"),
        title = title,
        imgUrl = if(coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        avgRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        description = description,
        imgUrl = imgUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        avgRatings = avgRating,
        ratingsCount = ratingCount,
        numPagesMedian = numPages,
        numEditions = numEditions
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        description = description,
        imgUrl = imgUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        avgRating = avgRatings,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions
    )
}