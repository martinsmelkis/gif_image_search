package com.example.gifimagesearch.model

data class ImageResponse(val id: String,
                        val title: String,
                        val url: String,
                        val slug: String,
                        val images: ImageResponseImagesPart)