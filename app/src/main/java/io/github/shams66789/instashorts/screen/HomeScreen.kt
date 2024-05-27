package io.github.shams66789.instashorts.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: GetNewsViewModel) {
    val res = viewModel.res.value?.articles


}