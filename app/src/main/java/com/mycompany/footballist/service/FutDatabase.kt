package com.mycompany.footballist.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mycompany.footballist.model.country.Country
import com.mycompany.footballist.model.league.League

@Database(entities = [Country::class, League::class], version = 1)
abstract class FutDatabase : RoomDatabase() {

    abstract fun footballDao() : FootballDao

    companion object{

        @Volatile private var instance : FutDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,FutDatabase::class.java,"database"
        ).build()

    }



}