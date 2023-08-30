package com.example.gifimagesearch.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gifimagesearch.model.ImageData
import com.example.gifimagesearch.R
import com.example.gifimagesearch.theme.AppTheme
import com.example.gifimagesearch.viewmodels.SearchImagesViewModel

@Composable
fun SearchResultsGridComposable(
    searchResults: List<ImageData>,
    viewModel: SearchImagesViewModel
) {
    Column {
        Text(
            text = stringResource(R.string.search_count, searchResults.size),
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.textPrimary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )
        val pagingItems = viewModel.searchResults.collectAsLazyPagingItems()

        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = Dp(160f))) {
            items(pagingItems.itemCount) { index ->
                SearchResultsCardItem(pagingItems[index]!!)
            }
        }
    }
}