package com.mycompany.footballist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.footballist.model.country.CountryResponse
import com.mycompany.footballist.model.league.League
import com.mycompany.footballist.model.league.LeagueResponse
import com.mycompany.footballist.service.FootballApi
import com.mycompany.footballist.service.FootballApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LeagueFeedViewModel: ViewModel() {


    private val footballApiService = FootballApiService()
    private val disposable = CompositeDisposable()
    var currentSeasonArray : ArrayList<Int> = ArrayList()
    var currentSeason : Int = 0


    val leagues = MutableLiveData<List<League>>()
    val leagueError = MutableLiveData<Boolean>()
    val leagueLoading = MutableLiveData<Boolean>()

    fun refreshLeagueData(countryCode: String){
        getLeaguesFromApi(countryCode)
    }

    private fun getLeaguesFromApi(countryCode : String){
        leagueLoading.value = true

        disposable.add(
            footballApiService.getLeagueList(countryCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LeagueResponse>(){
                    override fun onSuccess(t: LeagueResponse) {
                        val leagueArray = ArrayList<League>()
                        var index = 0
                        while(index<t.response.size){
                            leagueArray.add(t.response[index].league)
                            currentSeason = t.response[index].seasons[t.response[index].seasons.size-1].year //get the last index of the seasons list. To get the current season.
                            index++
                        }

                        leagues.value = leagueArray
                        println("size"+leagueArray.size)
                        leagueLoading.value = false
                        leagueError.value = false
                    }

                    override fun onError(e: Throwable) {
                        leagueLoading.value = false
                        leagueError.value = true
                    }

                })

        )




    }


}