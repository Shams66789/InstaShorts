package io.github.shams66789.instashorts.api_network

data class NewsDataModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)