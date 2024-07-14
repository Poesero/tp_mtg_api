package data

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import data.dbLocal.AppDatabase
import data.dbLocal.toCardList
import data.dbLocal.toCardLocalList
import model.Card
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CardsDataSource {
    companion object {
        private val db = FirebaseFirestore.getInstance()
        private val api: CardsAPI
        private val API_BASE_URL = "https://api.scryfall.com/"
        private val _TAG = "API-CHECK"

        init {
            api = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CardsAPI::class.java)
        }

         fun getCardsColors(name: String, color: String): ArrayList<Card> {
            Log.d(_TAG, "Cards Datasource GetColorCards")

            return try {
                val query = "$name color>=$color"
                Log.d(_TAG, query)
                val response = api.getCardsColors(query).execute()

                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d(_TAG, "Resultado Exitoso")
                    Log.d(_TAG, "${result?.data} \n size:${result?.data?.size}")
                    result?.data ?: ArrayList()
                } else {
                    Log.e(_TAG, "Error en llamado API: ${response.errorBody()?.string()}")
                    ArrayList()
                }
            } catch (e: Exception) {
                Log.e(_TAG, "Error en llamado API: ${e.message}")
                ArrayList()
            }
        }

        fun getRandom(): Card? {
            val api = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CardsAPI::class.java)
            return try {
                val response = api.getRandom().execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e(_TAG, "Error en llamado API: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e(_TAG, "Error en llamado API: ${e.message}")
                null
            }
        }

        fun getCards(name: String, context: Context): ArrayList<Card> {
            Log.d(_TAG, "Cards Datasource Get")

            val db = AppDatabase.getInstance(context)
            AppDatabase.clean(context)
            val cardsLocal = db.cardsDao().getAll()
            if (cardsLocal.isNotEmpty()) {
                Log.d(_TAG, "Returning cards from local database")
                return cardsLocal.toCardList() as ArrayList<Card>
            }

            val response = api.getCards(name).execute()

            return if (response.isSuccessful) {
                val cardResult = response.body()
                val cardList = cardResult?.data ?: ArrayList()

                if (cardList.isNotEmpty()) {
                    db.cardsDao().save(*cardList.toCardLocalList().toTypedArray())
                }

                Log.d(_TAG, "Cards fetched from API: ${cardList.size}")
                cardList
            } else {
                Log.e(_TAG, "Error fetching cards: ${response.errorBody()?.string()}")
                ArrayList()
            }
        }


    }
}