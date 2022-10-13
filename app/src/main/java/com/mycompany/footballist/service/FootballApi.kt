package com.mycompany.footballist.service

import com.mycompany.footballist.model.standings.StandingsResponse
import com.mycompany.footballist.model.country.CountryResponse
import com.mycompany.footballist.model.league.LeagueResponse
import com.mycompany.footballist.model.news.FootballNewsResponse
import com.mycompany.footballist.model.players.PlayerResponse
import com.mycompany.footballist.model.playerstats.PlayerStatsResponse
import com.mycompany.footballist.model.teams.TeamResponse
import com.mycompany.footballist.utils.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface FootballApi {



    @Headers("X-RapidAPI-Key: $API_KEY")
    @GET(GET_COUNTRIES)
    fun getCountries() : Single<CountryResponse>


    @Headers("X-RapidAPI-Key: $API_KEY")
    @GET(GET_LEAGUES_BY_COUNTRY)
    fun getLeagues(@Query("code")code:String) : Single<LeagueResponse>

    @Headers("X-RapidAPI-Key: $API_KEY")
    @GET(GET_STANDINGS)
    fun getStandings(@Query("league")league:Int,@Query("season")season: Int) : Single<StandingsResponse>

    @Headers("X-RapidAPI-Key: $API_KEY")
    @GET(GET_TEAMS)
    fun getTeams(@Query("id")id:Int) : Single<TeamResponse>

    @Headers("X-RapidAPI-Key: $API_KEY")
    @GET(GET_PLAYERS)
    fun getPlayers(@Query("team")team:Int) : Single<PlayerResponse>

    @Headers("X-RapidAPI-Key: $API_KEY")
    @GET(GET_PLAYER_STATS)
    fun getPlayerStats(@Query("id")playerId: Int, @Query("season")season: Int) : Single<PlayerStatsResponse>

    @Headers("X-RapidAPI-Key: $API_KEY")
    @GET(GET_TOP_SCORERS)
    fun getTopScorers(@Query("league")league:Int,@Query("season")season: Int)

    @GET(GET_SPORTS_NEWS)
    fun getFootballNews(@Query("country")country:String = "gb", @Query("category")category:String="sports", @Query("pageSize")pageSize:Int = 100, @Query("apiKey")apiKey:String = NEWS_API_KEY) : Single<FootballNewsResponse>



}