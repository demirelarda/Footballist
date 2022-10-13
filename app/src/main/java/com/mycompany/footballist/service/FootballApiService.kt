package com.mycompany.footballist.service

import com.mycompany.footballist.model.country.CountryResponse
import com.mycompany.footballist.model.league.LeagueResponse
import com.mycompany.footballist.model.players.PlayerResponse
import com.mycompany.footballist.model.playerstats.PlayerStatsResponse
import com.mycompany.footballist.model.standings.StandingsResponse
import com.mycompany.footballist.model.teams.TeamResponse
import com.mycompany.footballist.utils.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class FootballApiService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FootballApi::class.java)

    fun getCountryList() : Single<CountryResponse>{
        return api.getCountries()
    }

    fun getLeagueList(countryCode : String): Single<LeagueResponse>{
        return api.getLeagues(countryCode)
    }

    fun getStandings(league: Int, season: Int): Single<StandingsResponse>{
        return api.getStandings(league,season)
    }

    fun getTeam(id: Int): Single<TeamResponse>{
        return api.getTeams(id)
    }

    fun getPlayers(team: Int) : Single<PlayerResponse>{
        return api.getPlayers(team)
    }

    fun getPlayerStats(playerId: Int, season: Int) : Single<PlayerStatsResponse>{
        return api.getPlayerStats(playerId,season)
    }





}