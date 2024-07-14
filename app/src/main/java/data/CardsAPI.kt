package data


import model.Card
import model.CardResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsAPI {
    @GET("cards/search")
    fun getCards(@Query("q") name : String) : Call<CardResult>

    @GET("cards/random")
    fun getRandom() : Call<Card>

    @GET("cards/search")
    fun getCardsColors(@Query("q") query : String): Call<CardResult>
}