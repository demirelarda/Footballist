package com.mycompany.footballist.model.country

import com.mycompany.footballist.model.common.Paging

data class CountryResponse (
    val get: String,
    val parameters: List<Any>,
    val errors: List<Any>,
    val results: Int,
    val paging: Paging,
    val response: List<Country>
)