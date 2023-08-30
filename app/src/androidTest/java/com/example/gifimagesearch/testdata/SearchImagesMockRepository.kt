package com.example.gifimagesearch.testdata

import com.example.gifimagesearch.model.ImageData
import com.example.gifimagesearch.model.SearchCategoryCollection
import com.example.gifimagesearch.model.SearchSuggestionGroup
import com.example.gifimagesearch.repository.ISearchImagesRepository
import javax.inject.Inject

class SearchImagesMockRepository@Inject constructor(): ISearchImagesRepository {
    override suspend fun searchImages(keyWord: String, limit: Int, offset: Int): List<ImageData> {
        return if (keyWord == "Cat") {
            listOf( ImageData(
                id = 19L,
                name = "Cat name",
                imageUrl = "https://media4.giphy.com/media/BXjqytvu9bKzCUHdzz/100w.gif?cid=7f8a0c740owt5idtfe3rxlbswnat7qlqcubkqopi3ztg21yl&ep=v1_stickers_search&rid=100w.gif&ct=s"
            ))
        } else if (keyWord == "Dog") {
            listOf( ImageData(
                id = 20L,
                name = "Dog name",
                imageUrl = "https://media2.giphy.com/media/l4FGI8GoTL7N4DsyI/giphy.gif?cid=7f8a0c74fzwemcwqzeie0i1jyrenlsq4vkqbb62456sz6zxv&ep=v1_stickers_search&rid=giphy.gif&ct=s"
            ))
        } else {
            listOf()
        }
    }

    override fun getCategories(): List<SearchCategoryCollection> {
        return listOf()
    }

    override fun getSuggestions(): List<SearchSuggestionGroup> {
        return listOf()
    }

}