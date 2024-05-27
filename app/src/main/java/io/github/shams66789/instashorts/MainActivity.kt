package io.github.shams66789.instashorts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import io.github.shams66789.instashorts.screen.Result
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.shams66789.instashorts.screen.GetNewsViewModel
import io.github.shams66789.instashorts.screen.HomeScreen
import io.github.shams66789.instashorts.ui.theme.InstaShortsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: GetNewsViewModel by viewModels()
        setContent {
            InstaShortsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        when (val result = viewModel.res.value) {
                            is Result.Loading -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            is Result.Success -> {
                                result.data?.let {
                                    HomeScreen(viewModel = viewModel)
                                }
                            }
                            is Result.Error -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = result.message ?: "Unknown error")
                                    Button(onClick = { viewModel.fetchNews() }) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                            else -> {
                                // Log or handle unexpected cases
                                Text(text = "Unexpected state")
                            }
                        }
                    }
                }
            }
        }
    }
}

