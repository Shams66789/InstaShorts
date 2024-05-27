package io.github.shams66789.instashorts.repo

import io.github.shams66789.instashorts.api_network.ApiProvider
import io.github.shams66789.instashorts.api_network.NewsDataModel
import retrofit2.Response

class Repo {
    suspend fun getNewsData(): Response<NewsDataModel> {
        return ApiProvider.BuildApi().getNewsFromServer()
    }
}