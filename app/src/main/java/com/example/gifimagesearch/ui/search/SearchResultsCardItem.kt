package com.example.gifimagesearch.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.gifimagesearch.ui.components.PreviewImage
import com.example.gifimagesearch.model.ImageData

import com.example.gifimagesearch.theme.AppTheme

@Composable
fun SearchResultsCardItem(
    imageData: ImageData,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .width(150.dp)
            .fillMaxHeight()
    ) {
        val (divider, image, name, tag) = createRefs()
        createVerticalChain(image, name, tag, chainStyle = ChainStyle.Packed)
        PreviewImage(
            imageUrl = imageData.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .constrainAs(image) {
                    linkTo(
                        top = parent.top,
                        topMargin = 16.dp,
                        bottom = parent.bottom,
                        bottomMargin = 16.dp,
                        start = parent.start,
                        end = parent.end,
                    )
                }
        )
        Text(
            text = imageData.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.colors.textSecondary,
            modifier = Modifier.constrainAs(name) {
                linkTo(
                    top = image.bottom,
                    topMargin = 16.dp,
                    bottom = parent.bottom,
                    bottomMargin = 16.dp,
                    start = parent.start,
                    end = parent.end,
                )
            }
        )
        Spacer(modifier = Modifier.width(0.dp).height(16.dp))
    }
}