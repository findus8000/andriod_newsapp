package com.example.newsapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun NewsImage(imageUrl: String) {
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).build()
        )

    Image(
        painter = painter,
        contentDescription = "News image",
        modifier = Modifier
            .size(90.dp)
    )
}