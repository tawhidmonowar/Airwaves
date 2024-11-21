package org.tawhid.airwaves.di


import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.tawhid.airwaves.book.openbook.data.database.SaveBookDatabase
import org.tawhid.airwaves.book.openbook.data.network.KtorRemoteBookDataSource
import org.tawhid.airwaves.book.openbook.data.network.RemoteBookDataSource
import org.tawhid.airwaves.book.openbook.data.repository.DefaultBookRepository
import org.tawhid.airwaves.book.openbook.domain.BookRepository
import org.tawhid.airwaves.book.openbook.presentation.SelectedBookViewModel
import org.tawhid.airwaves.book.openbook.presentation.book_detail.BookDetailViewModel
import org.tawhid.airwaves.book.openbook.presentation.book_list.BookListViewModel
import org.tawhid.airwaves.book.presentation.BookHomeViewModel
import org.tawhid.airwaves.core.data.database.DatabaseFactory
import org.tawhid.airwaves.core.data.network.HttpClientFactory
import org.tawhid.airwaves.player.PlayerRepository
import org.tawhid.airwaves.player.PlayerRepositoryImpl
import org.tawhid.airwaves.player.PlayerViewModel
import org.tawhid.airwaves.presentations.settings.SettingViewModel
import org.tawhid.airwaves.utils.AppPreferences


expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()

    singleOf(::DefaultBookRepository).bind<BookRepository>()
    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()

    singleOf(::AppPreferences)

    single {
        get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build()
    }

    single { get<SaveBookDatabase>().saveBookDao }

    viewModelOf(::BookHomeViewModel)
    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::PlayerViewModel)
    viewModelOf(::SettingViewModel)
}