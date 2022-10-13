package com.mycompany.footballist.model.league

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class League(
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "logo")
    val logo: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}