package com.example.newsapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newsapp.model.Odds
import org.json.JSONArray

@Composable
fun MarketItem(
    market: Odds.Market,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onExpandToggle)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = market.question,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = if (isExpanded) 8.dp else 0.dp)
            )
            Text(
                text = "Volume: ${String.format("%.1f", market.volume.toFloat())}$",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = if (isExpanded) 8.dp else 0.dp)
            )

            if (isExpanded) {
                Text(
                    text = market.description ?: "No description available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            val outcomeList = JSONArray(market.outcomes)
            val priceList = JSONArray(market.outcomePrices)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 0 until outcomeList.length()) {
                    val outcome = outcomeList.getString(i)
                    val price = priceList.getString(i)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = when (outcome) {
                                    "Yes" -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                                    "No" -> Color(0xFFF44336).copy(alpha = 0.1f)
                                    else -> Color.Transparent
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = outcome,
                                style = MaterialTheme.typography.bodyLarge,
                                color = when (outcome) {
                                    "Yes" -> Color(0xFF4CAF50)
                                    "No" -> Color(0xFFF44336)
                                    else -> LocalContentColor.current
                                }
                            )
                            Text(
                                text = "${(price.toFloat() * 100).toInt()}%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}