package org.tawhid.airwaves.di


import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

import org.tawhid.airwaves.player.PlayerRepository
import org.tawhid.airwaves.player.PlayerRepositoryImpl
import org.tawhid.airwaves.player.PlayerViewModel

expect val platformModule: Module

val sharedModule = module {
    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
    viewModelOf(::PlayerViewModel)
}