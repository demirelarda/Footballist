package com.mycompany.footballist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.footballist.model.players.Player
import com.mycompany.footballist.model.players.PlayerResponse
import com.mycompany.footballist.service.FootballApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PlayersViewModel :  ViewModel() {

    private val footballApiService = FootballApiService()
    private val disposable = CompositeDisposable()
    private var playerArray = ArrayList<Player>()

    val players = MutableLiveData<List<Player>>()
    val playersLoading = MutableLiveData<Boolean>()
    val playersError = MutableLiveData<Boolean>()

    fun refreshTeamPlayers(team: Int){
        getPlayersFromApi(team)
    }


    private fun getPlayersFromApi(team: Int){
        playersLoading.value = true
        disposable.add(
            footballApiService.getPlayers(team)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PlayerResponse>(){
                    override fun onSuccess(t: PlayerResponse) {
                        for(player in t.response[0].players){
                            playerArray.add(player)
                        }
                        players.value = playerArray
                        playersLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        playersLoading.value = false
                        playersError.value = true
                        e.printStackTrace()
                    }

                })

        )

    }





}