package io.github.shams66789.instashorts.api_network

import io.github.shams66789.instashorts.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    fun BuildApi() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient
            .Builder()
            .build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiServices::class.java)
}