package org.tawhid.airwaves.radio.domain

import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result

interface RadioRepository {
    suspend fun searchRadios(query: String): Result<List<Radio>, DataError.Remote>
    suspend fun getTrendingRadios(): Result<List<Radio>, DataError.Remote>
    suspend fun getVerifiedRadios(): Result<List<Radio>, DataError.Remote>
    suspend fun getLatestRadios(): Result<List<Radio>, DataError.Remote>
}