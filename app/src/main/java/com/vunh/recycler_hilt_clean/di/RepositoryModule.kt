package com.vunh.recycler_hilt_clean.di

import com.vunh.recycler_hilt_clean.repository.RecyclerRepository
import com.vunh.recycler_hilt_clean.repository.RecyclerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun provideRecyclerRepository(recyclerRepositoryImpl: RecyclerRepositoryImpl): RecyclerRepository
}