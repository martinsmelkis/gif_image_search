package com.example.gifimagesearch.di

import com.example.gifimagesearch.repository.ISearchImagesRepository
import com.example.gifimagesearch.testdata.SearchImagesMockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [SearchImagesRepositoryModule::class]
)
abstract class SearchRepositoryMockModule {

    @Binds
    internal abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchImagesMockRepository): ISearchImagesRepository


}