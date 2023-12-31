package com.example.gifimagesearch.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems

import com.example.gifimagesearch.ui.components.LineDivider
import com.example.gifimagesearch.ui.components.AppScaffold
import com.example.gifimagesearch.ui.components.AppSurface
import com.example.gifimagesearch.theme.AppTheme

import com.example.gifimagesearch.model.*
import com.example.gifimagesearch.viewmodels.SearchImagesViewModel
import com.example.gifimagesearch.R
import com.example.gifimagesearch.repository.SearchImagesRepositoryImpl

@Composable
fun SearchComposable(
    modifier: Modifier = Modifier,
    state: SearchState = rememberSearchState(),
    viewModel: SearchImagesViewModel
) {
    val pagingItems = viewModel.searchResults.collectAsLazyPagingItems()
    // Orientation change - check if results already there, if so, then display
    var searchState: SearchDisplay = state.searchDisplay
    if (viewModel.resultsSize > 0) searchState = SearchDisplay.Results

    AppScaffold(
        modifier = modifier
    ) { paddingValues ->
        AppSurface(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Column {
                Spacer(modifier = Modifier.statusBarsPadding())
                SearchBar(
                    query = state.query,
                    onQueryChange = { state.query = it },
                    searchFocused = state.focused,
                    onSearchFocusChange = { state.focused = it },
                    onClearQuery = { state.query = TextFieldValue("") },
                    searching = state.searching
                )
                LineDivider()

                LaunchedEffect(state.query.text) {
                    viewModel.keyWord = state.query.text
                    if (state.query.text.isNotEmpty()) {
                        viewModel.invalidatePaging()
                        state.searching = true
                        viewModel.searchResults.collect { state.searching = false }
                        state.searchResults = pagingItems.itemSnapshotList.items

                    } else {
                        state.searching = false
                    }
                }
                when (searchState) {
                    SearchDisplay.Categories -> SearchCategories(state)
                    SearchDisplay.Suggestions -> SearchSuggestions(
                        suggestions = state.suggestions,
                        onSuggestionSelect = { suggestion ->
                            state.query = TextFieldValue(suggestion)
                        }
                    )
                    SearchDisplay.Results -> SearchResultsGridComposable(
                        pagingItems.itemSnapshotList.items,
                        viewModel
                    )
                    SearchDisplay.NoResults -> NoResults(query = state.query.text)
                }
            }
        }
    }
}

enum class SearchDisplay {
    Categories, Suggestions, Results, NoResults
}

@Composable
private fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    // TODO get from actual service
    categories: List<SearchCategoryCollection> = SearchImagesRepositoryImpl().getCategories(),
    suggestions: List<SearchSuggestionGroup> = SearchImagesRepositoryImpl().getSuggestions()
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
            categories = categories,
            suggestions = suggestions
        )
    }
}

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    categories: List<SearchCategoryCollection>,
    suggestions: List<SearchSuggestionGroup>,
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var categories by mutableStateOf(categories)
    var suggestions by mutableStateOf(suggestions)
    var searchResults by mutableStateOf(placeholderImages)
    val searchDisplay: SearchDisplay
        get() = when {
            !focused && query.text.isEmpty() -> SearchDisplay.Categories
            focused && query.text.isEmpty() -> SearchDisplay.Suggestions
            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }
}

@Composable
private fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    searchFocused: Boolean,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    modifier: Modifier = Modifier
) {
    AppSurface(
        color = AppTheme.colors.uiFloated,
        contentColor = AppTheme.colors.textSecondary,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (query.text.isEmpty()) {
                SearchHint()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
            ) {
                if (searchFocused) {
                    IconButton(onClick = onClearQuery) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            tint = AppTheme.colors.iconPrimary,
                            contentDescription = stringResource(R.string.label_back)
                        )
                    }
                }
                BasicTextField(
                    value = query,
                    textStyle = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.textSecondary),
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        }.testTag("searchTextField")
                )
                if (searching) {
                    CircularProgressIndicator(
                        color = AppTheme.colors.iconPrimary,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(36.dp)
                    )
                } else {
                    Spacer(Modifier.width(IconSize)) // balance arrow icon
                }
            }
        }
    }
}

private val IconSize = 48.dp

@Composable
private fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            tint = AppTheme.colors.textHelp,
            contentDescription = stringResource(R.string.label_search)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.label_search),
            color = AppTheme.colors.textHelp
        )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun SearchBarPreview() {
    AppTheme {
        AppSurface {
            SearchBar(
                query = TextFieldValue(""),
                onQueryChange = { },
                searchFocused = false,
                onSearchFocusChange = { },
                onClearQuery = { },
                searching = false
            )
        }
    }
}