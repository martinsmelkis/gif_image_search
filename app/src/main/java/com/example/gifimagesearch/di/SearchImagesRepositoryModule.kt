package com.example.gifimagesearch.di

import com.example.gifimagesearch.repository.ISearchImagesRepository
import com.example.gifimagesearch.repository.SearchImagesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchImagesRepositoryModule {

    @Binds
    internal abstract fun bindSearchRepository(searchRepositoryImpl: SearchImagesRepositoryImpl): ISearchImagesRepository


}