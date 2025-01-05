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
    val painter = // Optional: Adds a crossfade animation when the image loads
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true) // Optional: Adds a crossfade animation when the image loads
                }).build()
        )

    Image(
        painter = painter,
        contentDescription = "News image",
        modifier = Modifier
            .size(90.dp)
    )
}