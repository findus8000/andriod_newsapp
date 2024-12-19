package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.theme.NewsappTheme
import com.example.newsapp.viewmodel.NewsOddsVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.view.HomePage

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
fun NewsApp(vm: NewsOddsVM){
    Box(modifier = Modifier.background(Color.White)){
        //SensorPage(vm)
        HomePage(vm)
    }

}