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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface NewsOddsViewModel{
    val newsOddsState: StateFlow<NewsOddsState>

    fun getNews();
}

class NewsOddsVM(
    private val newsOdds:NewsOdds
): NewsOddsViewModel, ViewModel() {
    private val _newsOddsState = MutableStateFlow(NewsOddsState())
    override val newsOddsState: StateFlow<NewsOddsState>
        get() = _newsOddsState.asStateFlow()

    override fun getNews() {

        viewModelScope.launch {
            println("NEWWWWWWWWWWWWWWWWWS:")
            val data = newsOdds.getNews()

            _newsOddsState.value= newsOddsState.value.copy(newsList = data)
            println("Fetched: $data")

        }
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
        // Initialization code (if any)
       getNews()
    }
}
data class NewsOddsState(
    val newsList: News.NewsResponse = News.NewsResponse(emptyList())

)