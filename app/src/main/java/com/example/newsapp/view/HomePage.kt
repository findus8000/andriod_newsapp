package com.example.newsapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.viewmodel.NewsOddsViewModel

@Composable
fun HomePage(vm: NewsOddsViewModel){
    val newsOddsData by vm.newsOddsState.collectAsState()


    if (newsOddsData.newsList.articles.isEmpty()) {
        Text(
            text = "Empty", style = MaterialTheme.typography.titleLarge
        )
    }
    else
    {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Hello", style = MaterialTheme.typography.titleLarge
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 32.dp,
                    bottom = 130.dp
                ) // Optional padding for spacing between the text and the list
        ) {
            items(newsOddsData.newsList.articles){newsItem ->
               ArticleItem(newsItem)
            }

        }
    }
    }
}

