package org.tawhid.airwaves.radio.data.repository

import org.tawhid.airwaves.core.domain.DataError
import org.tawhid.airwaves.core.domain.Result
import org.tawhid.airwaves.core.domain.map
import org.tawhid.airwaves.radio.data.mappers.toRadio
import org.tawhid.airwaves.radio.data.network.RemoteRadioDataSource
import org.tawhid.airwaves.radio.domain.Radio
import org.tawhid.airwaves.radio.domain.RadioRepository

class RadioRepositoryImpl(
    private val remoteRadioDataSource: RemoteRadioDataSource
) : RadioRepository {
    override suspend fun searchRadios(query: String): Result<List<Radio>, DataError.Remote> {
        return remoteRadioDataSource.searchRadio(query).map { responseDtoList ->
            responseDtoList.map { it.toRadio() }
        }
    }

    override suspend fun getTrendingRadios(): Result<List<Radio>, DataError.Remote> {
        return remoteRadioDataSource.fetchTrendingRadios().map { responseDtoList ->
            responseDtoList.map { it.toRadio() }
        }
    }

    override suspend fun getVerifiedRadios(): Result<List<Radio>, DataError.Remote> {
        return remoteRadioDataSource.fetchVerifiedRadios().map { responseDtoList ->
            responseDtoList.map { it.toRadio() }
        }
    }

    override suspend fun getLatestRadios(): Result<List<Radio>, DataError.Remote> {
        return remoteRadioDataSource.fetchLatestRadios().map { responseDtoList ->
            responseDtoList.map { it.toRadio() }
        }
    }
}