package io.github.shams66789.instashorts.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.shams66789.instashorts.api_network.NewsDataModel
import io.github.shams66789.instashorts.repo.Repo
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GetNewsViewModel() : ViewModel() {

    var res = mutableStateOf<NewsDataModel?> (null)
    init {
        viewModelScope.launch {
            res.value = getNews(Repo())
        }
    }

    suspend fun getNews(repo: Repo) : NewsDataModel? {
        return repo.GetNewsData().body()
    }
}