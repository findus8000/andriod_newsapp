package com.example.newsapp.model

import android.app.Application
import android.content.Intent
import android.net.Uri


class NewsOdds(private val application: Application) {
    private val news = News()
    private var data = News.NewsResponse(emptyList())
    private val keyWordList = listOf(
            "and", "but", "or", "so", "yet", "nor", "for",
            "in", "on", "at", "to", "by", "with", "of", "from", "as",
            "the", "a", "an","found","inside",
            "i", "you", "he", "she", "it", "we", "they", "me", "him", "her", "us", "them",
            "very", "really", "just", "so", "too", "quite", "rather", "almost",
            "like", "well", "uh", "um", "oh", "yeah", "okay","why","can",
            "this", "that", "these", "those", "some", "such", "all", "any", "every", "each", "few", "many", "most",
            "through", "over", "out", "up", "down", "under", "above", "below", "between", "during", "after", "before",
            "says", "said", "report", "reported", "according", "would", "could", "should", "might", "will", "is", "was", "be", "been",
            ",", ".", "!", "?", ":", ";", "-", "--", "(", ")", "[", "]", "\"", "'"
    )


    suspend fun getNews(): News.NewsResponse {
        data = news.getNetNewsData()
        println("In NewsOdds: ${data.articles[8].title}")
        println("Keywords: " + removeKeywords(data.articles[8].title, keyWordList))
        println("Nouns: " + extractNouns(data.articles[8].title))
        return data
    }

    private fun removeKeywords(input: String, keywords: List<String>): String {
        // Split the input into words, filter out the keywords, and join back the remaining words
        return input
            .replace(Regex("\\p{Punct}"), "") // Remove punctuation
            .split(" ") // Split into words
            .filter { word -> word.lowercase() !in keywords } // Case-insensitive filtering
            .joinToString(" ") // Rejoin remaining words
    }

    private fun extractNouns(input: String): List<String> {
        // Regular expression to match words with an initial uppercase letter
        val regex = """\b[A-Z][a-z]*\b""".toRegex()

        // Find all words that match the regular expression
        return regex.findAll(input)
            .map { it.value }
            .toList()
    }

    fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Add this flag for non-Activity context
        application.startActivity(intent)
    }

}
