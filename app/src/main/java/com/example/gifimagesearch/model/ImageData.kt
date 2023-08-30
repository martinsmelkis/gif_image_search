/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gifimagesearch.model

import androidx.compose.runtime.Immutable

@Immutable
data class ImageData(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val tagline: String = "",
    val tags: Set<String> = emptySet()
)

/**
 * Static data
 */
val placeholderImages = listOf(
    ImageData(
        id = 1L,
        name = "",
        imageUrl = "https://media1.giphy.com/media/MlnnopxjMLwQeT5jdN/giphy.gif?cid=7f8a0c74b7rkexiv0187kqk1pvxf2b8c0vbn6djiielsnsaw&ep=v1_stickers_search&rid=giphy.gif&ct=s"
    ),
    ImageData(
        id = 2L,
        name = "",
        imageUrl = "https://media1.giphy.com/media/MlnnopxjMLwQeT5jdN/giphy.gif?cid=7f8a0c74b7rkexiv0187kqk1pvxf2b8c0vbn6djiielsnsaw&ep=v1_stickers_search&rid=giphy.gif&ct=s"
    )
)
