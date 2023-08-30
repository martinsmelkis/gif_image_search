package com.example.gifimagesearch.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import coil.ImageLoader
import com.example.gifimagesearch.model.ImageData
import com.example.gifimagesearch.network.ImagesPagingSource
import com.example.gifimagesearch.repository.ISearchImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private var repo: ISearchImagesRepository,
    internal var imageLoader: ImageLoader): ViewModel() {

    var resultsSize = 0
    var keyWord: String = ""
    var pagingSource : ImagesPagingSource? = null

    @OptIn(FlowPreview::class)
    var searchResults: Flow<PagingData<ImageData>> = Pager(PagingConfig(pageSize = 30)) {
        ImagesPagingSource(keyWord, this, repo).also { pagingSource = it }
    }.flow.cachedIn(viewModelScope).debounce(300)

    fun invalidatePaging() {
        resultsSize = 0
        pagingSource?.invalidate()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(javaClass.simpleName, "@@@@@@@@@@@@@ onCleared")
    }

}