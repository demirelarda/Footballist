package com.mycompany.footballist.model.news

data class FootballNewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)