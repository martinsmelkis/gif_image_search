package com.example.gifimagesearch.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gifimagesearch.model.ImageData
import com.example.gifimagesearch.repository.ISearchImagesRepository
import com.example.gifimagesearch.viewmodels.SearchImagesViewModel

class ImagesPagingSource(
    private val query: String,
    private val vm: SearchImagesViewModel,
    private val repo: ISearchImagesRepository
) : PagingSource<Int, ImageData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageData> {

        val offset = params.key ?: 10

        if (query.isEmpty()) {
            return LoadResult.Invalid()
        }

        val list = repo.searchImages(query, 10, offset)

        vm.resultsSize += 10
        val nextKey = vm.resultsSize + 10

        return LoadResult.Page(
            data = list,
            prevKey = null,
            nextKey = (if (params.key == nextKey) (nextKey + 10) else nextKey)
        )

    }

    override fun getRefreshKey(state: PagingState<Int, ImageData>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
}