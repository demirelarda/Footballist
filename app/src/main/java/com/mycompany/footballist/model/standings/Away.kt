package com.mycompany.footballist.model.standings

data class Away(
    val draw: Int,
    val goals: Goals,
    val lose: Int,
    val played: Int,
    val win: Int
)