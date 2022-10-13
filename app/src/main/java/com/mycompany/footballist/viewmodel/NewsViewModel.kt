package com.mycompany.footballist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.footballist.model.news.Article
import com.mycompany.footballist.model.news.FootballNewsResponse
import com.mycompany.footballist.model.players.PlayerResponse
import com.mycompany.footballist.model.playerstats.PlayerDetails
import com.mycompany.footballist.service.FootballNewsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class NewsViewModel : ViewModel() {

    private val footballNewsApiService = FootballNewsApiService()
    private val disposable = CompositeDisposable()

    val articleList = MutableLiveData<List<Article>>()
    val articleTitleList = MutableLiveData<List<String>>()
    val articleLoading = MutableLiveData<Boolean>()
    val articleError = MutableLiveData<Boolean>()

    fun refreshNews(){
        getNewsFromApi()
    }

    fun getNewsFromApi(){
        articleLoading.value = true

        disposable.add(
            footballNewsApiService.getUKFootballNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<FootballNewsResponse>(){
                    override fun onSuccess(t: FootballNewsResponse) {
                        articleLoading.value = false
                        articleList.value = t.articles
                    }

                    override fun onError(e: Throwable) {
                        articleLoading.value = true
                        articleError.value = true
                        e.printStackTrace()
                    }

                })

        )
    }


}