package com.makovskyi.booter.di.module

import javax.inject.Singleton

import android.content.Context

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import com.makovskyi.booter.data.database.AppDatabase
import com.makovskyi.booter.data.preferences.ConfigPreferences

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase(context)

    @Provides
    @Singleton
    fun provideConfigPreferences(@ApplicationContext context: Context): ConfigPreferences =
        ConfigPreferences(context)
}
