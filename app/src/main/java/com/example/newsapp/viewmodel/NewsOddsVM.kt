package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsapp.NewsApplication
import com.example.newsapp.model.NewsOdds
import kotlinx.coroutines.launch

interface NewsOddsViewModel{

}

class NewsOddsVM(
    private val newsOdds:NewsOdds
): NewsOddsViewModel, ViewModel() {


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
        viewModelScope.launch {
            println("NEWWWWWWWWWWWWWWWWWS:")
            println(newsOdds.getNews())
        }
    }
}