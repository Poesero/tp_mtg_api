package data

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import data.dbLocal.AppDatabase
import data.dbLocal.toCardList
import kotlinx.coroutines.delay
import model.Card
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CardsDataSource {
    val db = FirebaseFirestore.getInstance()
    private val _BASE_URL = "https://api.scryfall.com/"
    private val _TAG = "API-CHECK"

    suspend fun getCardsColors(name: String,color: String): ArrayList<Card>{
        Log.d(_TAG,"Cards Datasource GetColorCards")
        delay(5000)
        val api =Retrofit.Builder()
            .baseUrl(_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CardsAPI::class.java)
        return try {
            val query ="$name color>=$color"
            Log.d(_TAG,query)
            val result = api.getCardsColors(query)
            Log.d(_TAG, "Resultado Exitoso")
            Log.d(_TAG, "${result.data} \n size:${result.data.size}")
            result.data
        } catch (e: Exception) {
            Log.e(_TAG, "Error en llamado API: ${e.message}")
            ArrayList<Card>()
        }
    }

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

    suspend fun getCards(name: String, context: Context): ArrayList<Card>{
        Log.d(_TAG,"Cards Datasource Get")

        var  db = AppDatabase.getInstance(context)
        var cardLocal = db.cardsDao().getAll()
        if (cardLocal.size>0){
            return cardLocal.toCardList() as ArrayList<Card>
        }

        delay(5000)

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