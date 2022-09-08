package com.muume.cabinetto

import android.app.Application
import ch.comparis.challenge.testRepositoryModules
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class TestApplication : Application() {
    private val testModules: List<Module> = listOf(testRepositoryModules)
    private val logLevel: Level = Level.DEBUG
    private lateinit var koin: Koin
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    @Before
    private fun initKoin() {
        stopKoin()
        koin = startKoin {
            androidLogger(logLevel)
            androidContext(this@TestApplication)
            modules(testModules)
        }.koin
    }
}