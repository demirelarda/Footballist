package com.mycompany.footballist.model.playerstats

data class PlayerDetails(
    var passesOnTarget: Int = 0,
    var passesTotal: Int = 0,
    var shotsOnTarget: Int = 0,
    var shotsTotal: Int = 0,
    var totalDuel: Int = 0,
    var wonDuel: Int = 0,
    var goals: Int = 0,
    var playerHeight: String = "",
    var playerWeight: String = "",
    var playerName: String = "",
    var playerNationality: String =  "",
    var playerPhotoUrl: String = "",
    var playerAge: Int = 0,
    var assists: Int = 0,
    var savedShots: Int? = 0,
    var goalsConceded: Int = 0


)