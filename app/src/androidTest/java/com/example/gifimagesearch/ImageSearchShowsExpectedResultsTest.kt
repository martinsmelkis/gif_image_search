package com.example.gifimagesearch

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.gifimagesearch.testdata.SearchImagesMockRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ImageSearchShowsExpectedResultsTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var searchRepository: SearchImagesMockRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun search_list_is_displayed_successfully() = runTest {

        var searchText = "Cat"

        // Asserts the TextField has the corresponding value
        composeTestRule.onNodeWithTag("searchTextField").performTextInput(searchText)

        composeTestRule.onNodeWithTag("searchTextField").assert(hasText(searchText))
        composeTestRule.waitUntilAtLeastOneExists(hasText("Cat name"))

        composeTestRule.onAllNodesWithText("Cat name").assertAll(hasText("Cat name"))


        composeTestRule.onNodeWithTag("searchTextField").performTextClearance()
        composeTestRule.onNodeWithTag("searchTextField").assert(hasText(""))

        searchText = "Dog"
        composeTestRule.onNodeWithTag("searchTextField").performTextInput(searchText)

        composeTestRule.waitUntilAtLeastOneExists(hasText("Dog name"))

        composeTestRule.onAllNodesWithText("Dog name").assertAll(hasText("Dog name"))
    }

    @Test
    fun search_list_is_empty_on_missing_result() = runTest {

        val images = searchRepository.searchImages("CatDog", 10, 10)
        assert(images.isEmpty())

    }

}