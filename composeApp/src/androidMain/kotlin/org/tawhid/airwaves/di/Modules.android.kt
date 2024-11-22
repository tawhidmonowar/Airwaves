package org.tawhid.airwaves.di

import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.tawhid.airwaves.core.data.database.DatabaseFactory
import org.tawhid.airwaves.core.data.datastore.dataStorePreferences
import org.tawhid.airwaves.player.PlayerController

actual val platformModule = module {
    singleOf(::PlayerController)
    singleOf(::dataStorePreferences)
    single {
        DatabaseFactory(androidApplication())
    }
}