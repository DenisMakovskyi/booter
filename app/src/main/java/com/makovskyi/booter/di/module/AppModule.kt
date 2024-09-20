package com.makovskyi.booter.di.module

import javax.inject.Singleton

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.makovskyi.booter.di.qualifier.IODispatcher
import com.makovskyi.booter.di.qualifier.WorkerDispatcher

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @WorkerDispatcher
    fun provideWorkerDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
