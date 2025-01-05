package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.newsapp.ui.theme.NewsappTheme
import com.example.newsapp.viewmodel.NewsOddsVM
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.view.ArticleMarketsPage
import com.example.newsapp.view.NewsPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsappTheme {
                val newsOddsVM: NewsOddsVM = viewModel(
                    factory = NewsOddsVM.Factory
                )
                NewsApp(newsOddsVM)
            }
        }
    }
}

@Composable
fun NewsApp(vm: NewsOddsVM) {
    val navController = rememberNavController()

    Box(modifier = Modifier.background(Color.White)) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                NewsPage(vm = vm, navController = navController)
            }
            composable(
                route = "articleDetail/{articleTitle}/{articleSource}",
            ) { backStackEntry ->
                val articleTitle = backStackEntry.arguments?.getString("articleTitle")
                val articleSource = backStackEntry.arguments?.getString("articleSource")
                ArticleMarketsPage(vm, navController, articleTitle ?: "", articleSource ?: "")  // Pass the title to your detail screen
            }
        }
    }
}