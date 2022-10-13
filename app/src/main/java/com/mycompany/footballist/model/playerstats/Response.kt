package com.mycompany.footballist.model.playerstats

data class Response(
    val player: Player,
    val statistics: List<Statistic>
)