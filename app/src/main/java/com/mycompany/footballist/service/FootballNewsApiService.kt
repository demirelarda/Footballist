package com.mycompany.footballist.service

import com.mycompany.footballist.model.news.FootballNewsResponse
import com.mycompany.footballist.utils.NEWS_BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FootballNewsApiService {

    private val newsApi = Retrofit.Builder()
        .baseUrl(NEWS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FootballApi::class.java)


    fun getUKFootballNews(): Single<FootballNewsResponse>{
        return newsApi.getFootballNews()
    }



}