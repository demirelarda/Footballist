package com.mycompany.footballist.model.league

import com.mycompany.footballist.model.country.Country

data class Response(
    val country: Country,
    val league: League,
    val seasons: List<Season>
)