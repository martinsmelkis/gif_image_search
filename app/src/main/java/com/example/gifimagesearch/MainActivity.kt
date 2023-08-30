package com.example.gifimagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.gifimagesearch.viewmodels.SearchImagesViewModel
import com.example.gifimagesearch.ui.search.SearchComposable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val searchViewModel: SearchImagesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SearchComposable(viewModel = searchViewModel)
        }
    }

}