package data

import model.CardResult
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsAPI {
    @GET("cards/search")
    suspend fun getCards(@Query("q") name : String) : CardResult
}