package data

import model.Card
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsAPI {
    @GET("/search")
    suspend fun getCards(@Query("q") name : String) : Call<ArrayList<Card>>
}