package com.mycompany.footballist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.footballist.model.standings.Standing
import com.mycompany.footballist.model.standings.StandingsResponse
import com.mycompany.footballist.service.FootballApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LeagueStandingsViewModel : ViewModel(){

    private val footballApiService = FootballApiService()
    private val disposable = CompositeDisposable()

    val standings =  MutableLiveData<List<Standing>>()
    val standingsError = MutableLiveData<Boolean>()
    val standingsLoading = MutableLiveData<Boolean>()


    fun refreshLeagueStandings(league: Int, season: Int){
        getStandingsFromApi(league,season)
    }

    private fun getStandingsFromApi(league: Int, season: Int){
        standingsLoading.value = true
        disposable.add(
            footballApiService.getStandings(league,season)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<StandingsResponse>(){
                    override fun onSuccess(t: StandingsResponse) {
                        standingsLoading.value = false
                        //println(t.response[0].league.standings[0]) //list of standings
                        standings.value = t.response[0].league.standings[0]
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        standings.value = arrayListOf()
                        standingsLoading.value = false
                        standingsError.value = true
                    }

                })

        )
    }





}