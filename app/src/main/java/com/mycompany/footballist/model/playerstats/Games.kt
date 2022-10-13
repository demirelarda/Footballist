package com.mycompany.footballist.model.playerstats

data class Games(
    val appearences: Int,
    val captain: Boolean,
    val lineups: Int,
    val minutes: Int,
    val number: Any,
    val position: String,
    val rating: String
)