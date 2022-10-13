package com.mycompany.footballist.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mycompany.footballist.model.country.Country


@Dao
interface FootballDao {

    @Insert
    suspend fun insertAllCountries(vararg countries: Country) : List<Long>

    //@Insert
    //suspend fun insertAllLeagues(vararg leagues: League) : List<Long>

    @Query("SELECT * FROM Country")
    suspend fun getAllCountries() : List<Country>

    //@Query("SELECT * FROM League")
    //suspend fun getAllLeagues(): List<League>

    //@Query("SELECT * FROM League WHERE uuid = :uid")
    //suspend fun getSingleLeague(uid : Int) : League

    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()

    //@Query("DELETE FROM League")
    //suspend fun deleteAllLeagues()

}