package com.mycompany.footballist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.footballist.model.teams.Team
import com.mycompany.footballist.model.teams.TeamResponse
import com.mycompany.footballist.model.teams.Venue
import com.mycompany.footballist.service.FootballApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TeamDetailsViewModel : ViewModel() {

    private val footballApiService = FootballApiService()
    private val disposable = CompositeDisposable()

    val team = MutableLiveData<Team>()
    val teamVenue = MutableLiveData<Venue>()
    val teamError = MutableLiveData<Boolean>()
    val teamLoading = MutableLiveData<Boolean>()


    fun refreshTeam(id : Int){
        getTeamsFromApi(id)
    }

    private fun getTeamsFromApi(id: Int){
        teamLoading.value = true
        disposable.add(
            footballApiService.getTeam(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TeamResponse>(){
                    override fun onSuccess(t: TeamResponse) {
                        teamLoading.value = false
                        team.value = t.response[0].team
                        teamVenue.value = t.response[0].venue

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        teamLoading.value = false
                        teamError.value = true
                    }

                })

        )
    }










}