package com.example.gifimagesearch.repository

import com.example.gifimagesearch.network.ISearchApi
import com.example.gifimagesearch.network.SearchRetrofit
import com.example.gifimagesearch.BuildConfig
import com.example.gifimagesearch.model.*
import javax.inject.Inject

class SearchImagesRepositoryImpl @Inject constructor(): ISearchImagesRepository {
    override suspend fun searchImages(keyWord: String, limit: Int, offset: Int): List<ImageData> {
        val imagesApi = SearchRetrofit.getInstance().create(ISearchApi::class.java)

        val response = imagesApi.searchImages(keyWord, BuildConfig.GIPHY_API_KEY, limit, offset)

        val list = response.body()?.data?.map { r -> ImageData(
            id = r.id.hashCode().toLong(),
            name = r.title,
            tagline = r.slug,
            imageUrl = r.images.original.url
        ) } ?: emptyList()

        return list
    }

    override fun getCategories(): List<SearchCategoryCollection> = searchCategoryCollections
    override fun getSuggestions(): List<SearchSuggestionGroup> = searchSuggestions

    /**
     * Static data
     */
    private val searchCategoryCollections = listOf(
        SearchCategoryCollection(
            id = 0L,
            name = "Popular",
            categories = listOf(
                SearchCategory(
                    name = "Cats",
                    imageUrl = "https://media4.giphy.com/media/BXjqytvu9bKzCUHdzz/100w.gif?cid=7f8a0c740owt5idtfe3rxlbswnat7qlqcubkqopi3ztg21yl&ep=v1_stickers_search&rid=100w.gif&ct=s"
                ),
                SearchCategory(
                    name = "Dogs",
                    imageUrl = "https://media2.giphy.com/media/l4FGI8GoTL7N4DsyI/giphy.gif?cid=7f8a0c74fzwemcwqzeie0i1jyrenlsq4vkqbb62456sz6zxv&ep=v1_stickers_search&rid=giphy.gif&ct=s"
                ),
                SearchCategory(
                    name = "Desserts",
                    imageUrl = "https://media2.giphy.com/media/2gPMghJsdsmr1tFoWb/200.gif?cid=7f8a0c740tt7qqofa50isgwlca8fome67a61o2ajaielr4pt&ep=v1_stickers_search&rid=200.gif&ct=s"
                ),
                SearchCategory(
                    name = "Cars",
                    imageUrl = "https://media4.giphy.com/media/pn1yoeUyG5LXpVaKej/200.gif?cid=7f8a0c74bm25qgvzs9zvv2hrmcdp7fvfxc7g2m6gs3kiz2v6&ep=v1_stickers_search&rid=200.gif&ct=s"
                )
            )
        ),
        SearchCategoryCollection(
            id = 1L,
            name = "Lifestyles",
            categories = listOf(
                SearchCategory(
                    name = "Organic",
                    imageUrl = "https://source.unsplash.com/7meCnGCJ5Ms"
                ),
                SearchCategory(
                    name = "Gluten Free",
                    imageUrl = "https://source.unsplash.com/m741tj4Cz7M"
                ),
                SearchCategory(
                    name = "Paleo",
                    imageUrl = "https://source.unsplash.com/dt5-8tThZKg"
                ),
                SearchCategory(
                    name = "Vegan",
                    imageUrl = "https://source.unsplash.com/ReXxkS1m1H0"
                ),
                SearchCategory(
                    name = "Vegitarian",
                    imageUrl = "https://source.unsplash.com/IGfIGP5ONV0"
                ),
                SearchCategory(
                    name = "Whole30",
                    imageUrl = "https://source.unsplash.com/9MzCd76xLGk"
                )
            )
        )
    )

    private val searchSuggestions = listOf(
        SearchSuggestionGroup(
            id = 1L,
            name = "Popular searches",
            suggestions = listOf(
                "Organic",
                "Gluten Free",
                "Paleo",
                "Vegan",
                "Vegitarian",
                "Whole30"
            )
        )
    )
}