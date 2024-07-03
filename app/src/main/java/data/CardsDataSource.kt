package data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import model.Card

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CardsDataSource {
    val db = FirebaseFirestore.getInstance()
    private val _BASE_URL = "https://api.scryfall.com"
    private val _TAG = "API-CHECK"

    suspend fun getRandom(): Card{
        val api =Retrofit.Builder()
            .baseUrl(_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CardsAPI::class.java)
        return try {
            val result = api.getRandom()
            result
        } catch (e: Exception) {
            Log.e(_TAG, "Error en llamado API: ${e.message}")
            throw e
        }
    }

    suspend fun getCards(name: String): ArrayList<Card>{
        Log.d(_TAG,"Cards Datasource Get")

        val api =Retrofit.Builder()
            .baseUrl(_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CardsAPI::class.java)
        return try {
            val result = api.getCards(name)
            Log.d(_TAG, "Resultado Exitoso")
            Log.d(_TAG, "${result.data} \n size:${result.data.size}")
            result.data
        } catch (e: Exception) {
            Log.e(_TAG, "Error en llamado API: ${e.message}")
            ArrayList<Card>()
        }
    }
}