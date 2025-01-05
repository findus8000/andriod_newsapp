package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsapp.NewsApplication
import com.example.newsapp.model.News
import com.example.newsapp.model.NewsOdds
import com.example.newsapp.model.Odds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface NewsOddsViewModel {
    val newsOddsState: StateFlow<NewsOddsState>
    val currentMatchingMarkets: StateFlow<CurrentMatchingMarkets>

    fun getNews();
    fun openUrl(url: String)
    fun getRelatedMarkets(title: String)
    fun searchNews(string: String)
}

class NewsOddsVM(
    private val newsOdds: NewsOdds
) : NewsOddsViewModel, ViewModel() {
    private val _newsOddsState = MutableStateFlow(NewsOddsState())
    override val newsOddsState: StateFlow<NewsOddsState>
        get() = _newsOddsState.asStateFlow()

    private val _currentMatchingMarkets = MutableStateFlow(CurrentMatchingMarkets())
    override val currentMatchingMarkets: StateFlow<CurrentMatchingMarkets>
        get() = _currentMatchingMarkets.asStateFlow()

    override fun getNews() {
        viewModelScope.launch {
            val data = newsOdds.getNews()
            _newsOddsState.value = newsOddsState.value.copy(newsList = data)
            println("Fetched: $data")
        }
    }

    override fun searchNews(string: String) {
        viewModelScope.launch {
            val data = newsOdds.searchNews(string)
            _newsOddsState.value = newsOddsState.value.copy(newsList = data)
            println("Fetched: $data")
        }
    }

    override fun openUrl(url: String) {
        newsOdds.openUrlInBrowser(url)
    }

    override fun getRelatedMarkets(title: String) {
        val markets = newsOdds.matchMarkets(title)
        _currentMatchingMarkets.value = currentMatchingMarkets.value.copy(markets = markets)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NewsApplication)
                NewsOddsVM(application.newsOdds)
            }
        }
    }

    init {
        getNews()
        viewModelScope.launch {
           newsOdds.initOddsData()
        }
    }
}

data class NewsOddsState(
    val newsList: News.NewsResponse = News.NewsResponse(emptyList())
)

data class CurrentMatchingMarkets(
    val markets:List<Odds.Market> = emptyList()
)