package com.mycompany.footballist.model.standings

import com.mycompany.footballist.model.common.Paging

data class StandingsResponse(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)