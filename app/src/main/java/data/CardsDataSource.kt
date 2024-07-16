package data

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import data.dbLocal.AppDatabase
import data.dbLocal.FavoriteCard
import data.dbLocal.toCardList
import data.dbLocal.toCardLocalList
import kotlinx.coroutines.tasks.await
import model.Card
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

        suspend fun getCards(name: String, context: Context): ArrayList<Card> {
            Log.d(_TAG, "Cards Datasource Get")

            val db = AppDatabase.getInstance(context)
            AppDatabase.clean(context)

            val cardsLocal = db.cardsDao().getBySubstring(name)
            if (cardsLocal.isNotEmpty() && cardsLocal.any { it.name.contains(name) }) {
                Log.d(_TAG, "Returning cards from local database")
                return cardsLocal.toCardList() as ArrayList<Card>
            }

            val firestore = FirebaseFirestore.getInstance()
            val firestoreCards = suspendCoroutine<List<Card>?> { continuation ->
                firestore.collection("cards")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val cards = task.result?.toObjects(Card::class.java)?.filter { it.name.contains(name, ignoreCase = true) }
                            continuation.resume(cards)
                        } else {
                            continuation.resume(null)
                        }
                    }
            }

            return if (!firestoreCards.isNullOrEmpty()) {
                Log.d(_TAG, "Returning cards from Firestore")
                db.cardsDao().save(*firestoreCards.toCardLocalList().toTypedArray())
                firestoreCards as ArrayList<Card>
            } else {
                try {
                    val response = api.getCards(name).execute()

                    if (response.isSuccessful) {
                        val cardResult = response.body()
                        val cardList = cardResult?.data ?: ArrayList()

                        if (cardList.isNotEmpty()) {
                            val batch = firestore.batch()
                            cardList.forEach { card ->
                                val docRef = firestore.collection("cards").document(card.name.replace("//", "__"))
                                batch.set(docRef, card)
                            }
                            batch.commit().await()
                            Log.d(_TAG, "Cards data saved successfully to Firestore")

                            db.cardsDao().save(*cardList.toCardLocalList().toTypedArray())
                        }

                        Log.d(_TAG, "Cards fetched from API: ${cardList.size}")
                        cardList
                    } else {
                        Log.e(_TAG, "Error fetching cards: ${response.errorBody()?.string()}")
                        ArrayList()
                    }
                } catch (e: Exception) {
                    Log.e(_TAG, "Error fetching or saving cards data", e)
                    ArrayList()
                }
            }
        }





    }
}