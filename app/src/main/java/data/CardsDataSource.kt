package data

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import data.dbLocal.AppDatabase
import data.dbLocal.toCardList
import data.dbLocal.toCardLocalList
import kotlinx.coroutines.delay
import model.Card
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CardsDataSource {
    companion object {
        val db = FirebaseFirestore.getInstance()
        private val api: CardsAPI
        private val _BASE_URL = "https://api.scryfall.com/"
        private val _TAG = "API-CHECK"

        init {
            api = Retrofit.Builder()
                .baseUrl(_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CardsAPI::class.java)
        }

        suspend fun getCardsColors(name: String, color: String): ArrayList<Card> {
            Log.d(_TAG, "Cards Datasource GetColorCards")
            delay(3000)

            return try {
                val query = "$name color>=$color"
                Log.d(_TAG, query)
                val result = api.getCardsColors(query)
                Log.d(_TAG, "Resultado Exitoso")
                Log.d(_TAG, "${result.data} \n size:${result.data.size}")
                result.data
            } catch (e: Exception) {
                Log.e(_TAG, "Error en llamado API: ${e.message}")
                ArrayList<Card>()
            }
        }

        suspend fun getRandom(): Card {
            val api = Retrofit.Builder()
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

        suspend fun getCards(name: String, context: Context): ArrayList<Card> {
            Log.d(_TAG, "Cards Datasource Get")

            var db = AppDatabase.getInstance(context)
            var cardLocal = db.cardsDao().getAll()
            if (cardLocal.isNotEmpty()) {
                return cardLocal.toCardList() as ArrayList<Card>
            }

            delay(3000)

            var result = api.getCards(name).execute()
            return if (result.isSuccesful) {
                val cardList = result.body() ?: ArrayList<Card>()
                if (cardList.isNotEmpty()) {
                    db.cardsDao().save(*cardList.toCardLocalList().toTypedArray())
                }
                cardList
            } else {
                ArrayList<Card>()
            }
            /*
            return try {
                val result = api.getCards(name)
                Log.d(_TAG, "Resultado Exitoso")
                Log.d(_TAG, "${result.data} \n size:${result.data.size}")
                result.data
            } catch (e: Exception) {
                Log.e(_TAG, "Error en llamado API: ${e.message}")
                ArrayList<Card>()
            }*/

        }


        suspend fun getCard(name: String): Card? {

            var carta = suspendCoroutine<Card?> { continuation ->
                db.collection("cards").document(name).get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        var c = it.result.toObject(Card::class.java)
                        continuation.resume(c)
                    } else {
                        continuation.resume(null)
                    }
                }
            }

            if (carta != null) {
                return carta
            }
            delay(5000)
            var result = api.getCards(name).execute()
            if (result.isSuccesful) {
                val cards = result.body() ?: return null
                val card = cards.singleOrNull()

                return card
            } else {
                return null
            }

        }


    }
}