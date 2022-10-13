package com.mycompany.footballist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.footballist.model.players.Player
import com.mycompany.footballist.model.players.PlayerResponse
import com.mycompany.footballist.model.playerstats.PlayerDetails
import com.mycompany.footballist.model.playerstats.PlayerStatsResponse
import com.mycompany.footballist.model.playerstats.Statistic
import com.mycompany.footballist.service.FootballApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PlayerStatsViewModel : ViewModel() {

    private val footballApiService = FootballApiService()
    private val disposable = CompositeDisposable()

    val playerDetails = MutableLiveData<PlayerDetails>()
    val playerDetailsLoading = MutableLiveData<Boolean>()
    val playerDetailsError = MutableLiveData<Boolean>()

    fun refreshPlayerDetails(playerId: Int,season: Int){
        getPlayerDetailsFromApi(playerId,season)
    }

    private fun getPlayerDetailsFromApi(playerId: Int, season: Int){
        playerDetailsLoading.value = true
        disposable.add(
            footballApiService.getPlayerStats(playerId,season)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PlayerStatsResponse>(){
                    override fun onSuccess(t: PlayerStatsResponse) {
                        playerDetailsLoading.value = false
                        val passesOnTarget = t.response[0].statistics[0].passes.accuracy
                        val passesTotal = t.response[0].statistics[0].passes.total
                        val shotsOnTarget = t.response[0].statistics[0].shots.on
                        val shotsTotal = t.response[0].statistics[0].shots.total
                        val totalDuels = t.response[0].statistics[0].duels.total
                        val wonDuels = t.response[0].statistics[0].duels.won
                        val goals = t.response[0].statistics[0].goals.total

                        val playerStats = PlayerDetails()
                        playerStats.passesOnTarget = passesOnTarget
                        playerStats.passesTotal = passesTotal
                        playerStats.shotsOnTarget = shotsOnTarget
                        playerStats.shotsTotal = shotsTotal
                        playerStats.totalDuel = totalDuels
                        playerStats.goals = goals
                        playerStats.wonDuel = wonDuels
                        playerStats.playerHeight = t.response[0].player.height
                        playerStats.playerWeight = t.response[0].player.weight
                        playerStats.playerName = t.response[0].player.name
                        playerStats.playerNationality = t.response[0].player.nationality
                        playerStats.playerPhotoUrl = t.response[0].player.photo
                        playerStats.playerAge = t.response[0].player.age
                        playerStats.assists = t.response[0].statistics[0].goals.assists
                        playerStats.goalsConceded = t.response[0].statistics[0].goals.conceded
                        //playerStats.savedShots = t.response[0].statistics[0].goals.saves.toString().toDouble().toInt()
                        playerDetails.value = playerStats

                    }

                    override fun onError(e: Throwable) {
                        playerDetailsError.value = true
                    }

                })

        )
    }



}