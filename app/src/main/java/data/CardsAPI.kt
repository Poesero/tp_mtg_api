package data

import model.Card
import model.CardResult
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsAPI {
    @GET("cards/search")
    suspend fun getCards(@Query("q") name : String) : CardResult

    @GET("cards/random")
    suspend fun getRandom() : Card

    @GET("cards/search")
    suspend fun getCardsColors(@Query("q") query : String): CardResult
}