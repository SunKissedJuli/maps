package com.example.maps.di

import com.example.maps.utils.MapObjectHandler
import com.example.maps.utils.MapObjectHandlerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val managerModule = module {
    singleOf(::MapObjectHandlerImpl) {bind<MapObjectHandler>()}
}