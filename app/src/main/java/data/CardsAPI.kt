package data

import model.CardResult
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsAPI {
    @GET("/search")
    suspend fun getCards(@Query("q") name : String) : CardResult
}