package com.cruz.matchessimulator.data

import com.cruz.matchessimulator.domain.Match
import retrofit2.Call
import retrofit2.http.GET

interface MatchesAPI {
    @get:GET("matches.json")
    val matches: Call<List<Match?>?>?
}

