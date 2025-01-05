package com.example.newsapp.model

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


class NewsOdds(private val application: Application) {
    private val news = News()
    private val odds = Odds()
    private var oddsMarkets = emptyList<Odds.Market>()

    suspend fun getNews(): News.NewsResponse {
        val newsData = news.getBasicNewsData()
        newsData.articles.forEach { article ->
            article.title = article.title.split(" - ").firstOrNull()?.trim() ?: article.title
        }
        return newsData
    }

    suspend fun searchNews(string: String): News.NewsResponse {
        val newsData = news.searchNewsData(string)
        newsData.articles.forEach { article ->
            article.title = article.title.split(" - ").firstOrNull()?.trim() ?: article.title
        }
        return newsData
    }

    suspend fun initOddsData() {
        val numberOfChunks = 4 //4 = 4 * 2 months back
        val currentDate = OffsetDateTime.now()
        val allLists = mutableListOf<List<Odds.Market>>()

        for (i in 0 until numberOfChunks) {
            val endDate = if (i == 0) {
                currentDate
            } else {
                currentDate.minusMonths(i * 2L)
            }
            val startDate = currentDate.minusMonths((i + 1) * 2L)

            val list = odds.getNetOddsData(
                startDate = startDate.format(DateTimeFormatter.ISO_INSTANT),
                endDate = endDate.format(DateTimeFormatter.ISO_INSTANT),
                volume = 50000
            )

            allLists.add(list)
            println("LENGTH for chunk $i: ${list.size}")
        }

        oddsMarkets = allLists.flatten()
    }

    private fun extractKeywords(input: String): List<String> {
        val stopWords = setOf(
            "The", "This", "That", "These", "Those", "And", "But", "Or", "For",
            "Nor", "Yet", "So", "After", "Although", "As", "Because", "Before",
            "How", "If", "Once", "Since", "Than", "That", "Though", "Unless",
            "Until", "When", "Where", "Whether", "While", "Who", "Why", "What",
            "Which", "A", "An", "Are", "Is", "Was", "Were", "Be", "Being",
            "Been", "Have", "Has", "Had", "Does", "Did", "Can", "Could", "May",
            "Might", "Must", "Shall", "Should", "Will", "Would", "In", "Into",
            "By", "With", "From", "Of", "On", "At", "To", "Not", "Its", "It",
            "Their", "They", "His", "Her", "He", "She", "You", "Your", "We",
            "Our", "My", "Says", "Said", "New", "Just", "Now", "Top", "Breaking"
        )

        val regex = """\b[A-Z][a-zA-Z0-9]*\b""".toRegex()

        return regex.findAll(input)
            .map { it.value }
            .filter { it.length > 1 }
            .filter { !stopWords.contains(it) }
            .toList()
    }

    fun matchMarkets(title:String): List<Odds.Market> {
        val titleKeyWords = extractKeywords(title)
        Log.d("MARKET_TAG", "Keywords from title: $title, $titleKeyWords")

        return oddsMarkets.filter { market ->
            val marketQuestion = market.question.lowercase()
            val matches = titleKeyWords.any { keyword ->
                // Use word boundaries (\b) to match whole words only
                val pattern = "\\b${keyword.lowercase()}\\b".toRegex()
                val isMatch = pattern.containsMatchIn(marketQuestion)
                if (isMatch) {
                    Log.d("MARKET_TAG", "Match found: '$keyword' in market: '${market.question}'")
                }
                isMatch
            }
            matches
        }
    }

    fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Add this flag for non-Activity context
        application.startActivity(intent)
    }

}
