package com.ashr.cleanMvvmAir.core.di

import com.ashr.cleanMvvmAir.core.dispatcher.DispatcherAnnotations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Singleton
    @Provides
    @DispatcherAnnotations.Io
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    @DispatcherAnnotations.Main
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Singleton
    @Provides
    @DispatcherAnnotations.Default
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}