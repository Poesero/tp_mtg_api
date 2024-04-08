package com.example.tp_mtg_api.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsAPI {
    @GET("/search")
    fun getCards(@Query("q") name : String) : Call<ArrayList<Card>>
}