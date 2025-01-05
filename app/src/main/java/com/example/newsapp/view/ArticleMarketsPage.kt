package com.example.newsapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.viewmodel.NewsOddsViewModel

@Composable
fun ArticleMarketsPage(vm: NewsOddsViewModel, navController: NavController, articleTitle: String, articleSource: String) {
    val currentMatchingMarkets by vm.currentMatchingMarkets.collectAsState()

    val expandedMarkets = remember { mutableStateOf(mutableSetOf<String>()) }

    vm.getRelatedMarkets(articleTitle)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$articleTitle - $articleSource",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = spacedBy(12.dp)
        ) {
            if (currentMatchingMarkets.markets.isEmpty()){
                item {
                    Text("No matching markets found for this article.")
                }
            }else{
                items(currentMatchingMarkets.markets) { market ->
                    MarketItem(
                        market = market,
                        isExpanded = expandedMarkets.value.contains(market.question),
                        onExpandToggle = {
                            expandedMarkets.value = if (expandedMarkets.value.contains(market.question)) {
                                expandedMarkets.value.minus(market.question) as MutableSet<String>
                            } else {
                                expandedMarkets.value.plus(market.question) as MutableSet<String>
                            }
                        }
                    )
                }
            }
        }
        BackButton(navController)
    }
}

