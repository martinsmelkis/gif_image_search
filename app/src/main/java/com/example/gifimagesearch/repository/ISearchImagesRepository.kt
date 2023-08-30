package com.example.gifimagesearch.repository

import com.example.gifimagesearch.model.*

interface ISearchImagesRepository {
    suspend fun searchImages(keyWord: String, limit: Int, offset: Int): List<ImageData>

    fun getCategories(): List<SearchCategoryCollection>
    fun getSuggestions(): List<SearchSuggestionGroup>
}