package io.github.shams66789.instashorts.api_network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("top-headlines")
    suspend fun getNewsFromServer(
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy : String = "publishedAt",
        @Query("apiKey") apiKey: String = "4127539276744d7ea23361a5bfd59115"
    ) : Response<NewsDataModel>
}