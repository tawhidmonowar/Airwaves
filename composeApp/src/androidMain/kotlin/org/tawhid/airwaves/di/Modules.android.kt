package org.tawhid.airwaves.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.tawhid.airwaves.data.datastore.dataStorePreferences
import org.tawhid.airwaves.player.PlayerController

actual val platformModule = module {
    singleOf(::PlayerController)
    singleOf(::dataStorePreferences)
}