package com.example.newsapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.newsapp.model.News
import com.example.newsapp.viewmodel.NewsOddsViewModel

@Composable
fun ArticleItem(articleItem: News.Articles,vm: NewsOddsViewModel) {
    if (articleItem.title.contentEquals("[Removed]")){

    }
    else{
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "TITLE: ${articleItem.title}", style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f).padding(top = 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "DESC: ${articleItem.description}", style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f).padding(top = 10.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            if (articleItem.urlToImage!=null){
               NewsImage(articleItem.urlToImage)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Url: ${articleItem.url}", style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.weight(1f).padding(top = 10.dp)
                    .clickable {
                        vm.openUrl(articleItem.url)
                    }
            )
        }
    }

}
@Composable
fun NewsImage(imageUrl: String) {
    // Remember the painter for loading the image from the URL
    val painter = // Optional: Adds a crossfade animation when the image loads
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
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