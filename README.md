# gifimagesearch
Android Compose Gif images search App with modern Architecture

Uses search api by giphy: https://developers.giphy.com/docs/api/endpoint/#search

UI/Basic setup heavily inspired by https://github.com/android/compose-samples/tree/main - Jetsnack

!!!!!!!!!!!! NOTE

To be able to search with giphy API you need to add gradle.properties to main dir and add "giphy_apikey" entry with your own key

Possibly "kapt.use.worker.api=false" as well in case of build problems

Implementation:

UI: Compose with Material3

DI: Hilt - used to provide and mock images repository and Coil ImageLoader

Uses ViewModel to hold and cache data, such as images

Uses Paging3 to load images on demand

And other basic Jetpack/androidx dependencies

Tests:

Has one Instrumented test for the basic usecase: input text and check that expected result is displayed.

Uses DI with Hilt to switch images repository to have mock data.
