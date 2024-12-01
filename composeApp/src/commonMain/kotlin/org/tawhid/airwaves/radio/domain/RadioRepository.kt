package org.tawhid.airwaves.radio.domain

import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result

interface RadioRepository {
    suspend fun searchRadios(query: String): Result<List<Radio>, DataError.Remote>
}