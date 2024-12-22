package org.tawhid.airwaves.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.tawhid.airwaves.book.audiobook.data.network.RemoteAudioBookDataSource
import org.tawhid.airwaves.book.audiobook.data.network.RemoteAudioBookDataSourceImpl
import org.tawhid.airwaves.book.audiobook.data.repository.AudioBookRepositoryImpl
import org.tawhid.airwaves.book.audiobook.domain.AudioBookRepository
import org.tawhid.airwaves.book.audiobook.presentation.SelectedAudioBookViewModel
import org.tawhid.airwaves.book.audiobook.presentation.audiobook_detail.AudioBookDetailViewModel
import org.tawhid.airwaves.book.audiobook.presentation.audiobook_home.AudioBookViewModel
import org.tawhid.airwaves.book.openbook.data.database.SaveBookDatabase
import org.tawhid.airwaves.book.openbook.data.network.RemoteBookDataSource
import org.tawhid.airwaves.book.openbook.data.network.RemoteBookDataSourceImpl
import org.tawhid.airwaves.book.openbook.data.repository.BookRepositoryImpl
import org.tawhid.airwaves.book.openbook.domain.BookRepository
import org.tawhid.airwaves.book.openbook.presentation.SelectedBookViewModel
import org.tawhid.airwaves.book.openbook.presentation.book_detail.BookDetailViewModel
import org.tawhid.airwaves.book.openbook.presentation.book_home.BookListViewModel
import org.tawhid.airwaves.book.presentation.BookHomeViewModel
import org.tawhid.airwaves.core.data.database.DatabaseFactory
import org.tawhid.airwaves.core.data.network.HttpClientFactory
import org.tawhid.airwaves.core.player.domain.PlayerRepository
import org.tawhid.airwaves.core.player.data.PlayerRepositoryImpl
import org.tawhid.airwaves.core.player.presentation.PlayerViewModel
import org.tawhid.airwaves.core.setting.SettingViewModel
import org.tawhid.airwaves.core.utils.AppPreferences
import org.tawhid.airwaves.radio.data.network.RemoteRadioDataSource
import org.tawhid.airwaves.radio.data.network.RemoteRadioDataSourceImpl
import org.tawhid.airwaves.radio.data.repository.RadioRepositoryImpl
import org.tawhid.airwaves.radio.domain.RadioRepository
import org.tawhid.airwaves.radio.presentation.SelectedRadioViewModel
import org.tawhid.airwaves.radio.presentation.radio_detail.RadioDetailViewModel
import org.tawhid.airwaves.radio.presentation.radio_home.RadioHomeViewModel

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }

    singleOf(::RemoteBookDataSourceImpl).bind<RemoteBookDataSource>()
    singleOf(::RemoteAudioBookDataSourceImpl).bind<RemoteAudioBookDataSource>()
    singleOf(::RemoteRadioDataSourceImpl).bind<RemoteRadioDataSource>()

    singleOf(::BookRepositoryImpl).bind<BookRepository>()
    singleOf(::AudioBookRepositoryImpl).bind<AudioBookRepository>()
    singleOf(::RadioRepositoryImpl).bind<RadioRepository>()
    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
    single { PlayerViewModel(get()) }

    singleOf(::AppPreferences)

    single { get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build() }

    single { get<SaveBookDatabase>().saveBookDao }

    viewModelOf(::RadioHomeViewModel)
    viewModelOf(::BookHomeViewModel)
    viewModelOf(::BookListViewModel)
    viewModelOf(::AudioBookViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::RadioDetailViewModel)
    viewModelOf(::AudioBookDetailViewModel)
    viewModelOf(::SelectedAudioBookViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::SelectedRadioViewModel)
    viewModelOf(::SettingViewModel)
}