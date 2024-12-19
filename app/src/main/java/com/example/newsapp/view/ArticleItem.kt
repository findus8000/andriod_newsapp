package com.example.newsapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.model.News

@Composable
fun ArticleItem(articleItem: News.Articles) {
    if (articleItem.title.contentEquals("[Removed]")){

    }
    else{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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
            Text(
                text = "Url: ${articleItem.url}", style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f).padding(top = 10.dp)
            )
        }
    }

}