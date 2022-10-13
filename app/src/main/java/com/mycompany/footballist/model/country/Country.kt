package com.mycompany.footballist.model.country

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @ColumnInfo(name = "countryCode")
    val code: String?,

    @ColumnInfo(name = "flag")
    val flag: String?,

    @ColumnInfo(name = "name")
    val name: String
){

    @PrimaryKey(autoGenerate = true)
    var uuids: Int = 0

}