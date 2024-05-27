package io.github.shams66789.instashorts.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.shams66789.instashorts.api_network.NewsDataModel
import io.github.shams66789.instashorts.repo.Repo
import kotlinx.coroutines.launch
import retrofit2.Response

class GetNewsViewModel : ViewModel() {

    var res = mutableStateOf<Result<NewsDataModel>?>(null)

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            res.value = Result.Loading("Loading")
            try {
                val response = getNews(Repo())
                if (response != null && response.isSuccessful) {
                    res.value = Result.Success(response.body())
                } else {
                    res.value = Result.Error("Failed to load news")
                }
            } catch (e: Exception) {
                res.value = Result.Error("Error: ${e.message}")
            }
        }
    }

    private suspend fun getNews(repo: Repo): Response<NewsDataModel>? {
        return repo.getNewsData()
    }
}