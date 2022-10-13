package com.mycompany.footballist.model.league

import com.mycompany.footballist.model.common.Paging

data class LeagueResponse(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val leagueParameters: LeagueParameters,
    val response: List<Response>,
    val results: Int
)