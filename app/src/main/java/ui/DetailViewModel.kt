package ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import data.CardsRepo
import data.dbLocal.AppDatabase
import data.dbLocal.FavoriteCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import model.Card

class DetailViewModel : ViewModel() {
    private val _TAG = "API-CHECK"
    private val cardsRepo: CardsRepo = CardsRepo()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    var card: MutableLiveData<Card> = MutableLiveData()

    fun init(name: String) {
        coroutineScope.launch {
            kotlin.runCatching {
                cardsRepo.getCard(name)
            }.onSuccess { cards ->
                Log.d(_TAG, "Cards on success: ${card.value} ")
                if (cards.isNotEmpty()) {
                    card.postValue(cards[0])
                }
            }.onFailure {
                Log.e(_TAG, "Error fetching cards: $it")
                card.postValue(Card(name = "Error"))
            }
        }
    }

    fun fetchRandomCard() {
        coroutineScope.launch {
            kotlin.runCatching {
                cardsRepo.getRandom()
            }.onSuccess {
                card.postValue(it)
            }.onFailure {
                Log.e(_TAG, "Error fetching random card: $it")
                card.postValue(Card(name = "Error fetching random card"))
            }
        }
    }

    fun isFavorite(userId: String, cardName: String, context: Context): Boolean {
        val db = AppDatabase.getInstance(context)
        val favoriteCard = db.favoriteCardsDao().isFavorite(userId, cardName)
        return favoriteCard != null
    }

    fun addFavorite(userId: String, card: Card, context: Context) {
        val db = AppDatabase.getInstance(context)
        db.favoriteCardsDao().addFavorite(FavoriteCard(card.name, userId))

        val firestore = FirebaseFirestore.getInstance()
        val cardData = hashMapOf(
            "name" to card.name,
            "oracle_text" to card.oracle_text,
            "type_line" to card.type_line,
            "image_uris" to card.image_uris
        )

        firestore.collection("users")
            .document(userId)
            .collection("fav_cards")
            .document("cards")
            .collection("cards")
            .document(card.name)
            .set(cardData)
            .addOnSuccessListener {
                Log.d(_TAG, "Card successfully added to Firestore")
            }
            .addOnFailureListener { e ->
                Log.w(_TAG, "Error adding card to Firestore", e)
            }
    }


    fun removeFavorite(userId: String, card: Card, context: Context) {
        val db = AppDatabase.getInstance(context)
        val favoriteCard = db.favoriteCardsDao().isFavorite(userId, card.name)
        favoriteCard?.let {
            db.favoriteCardsDao().removeFavorite(it)
        }

        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("users")
            .document(userId)
            .collection("fav_cards")
            .document("cards")
            .collection("cards")
            .document(card.name)
            .delete()
            .addOnSuccessListener {
                Log.d(_TAG, "Card successfully removed from Firestore")
            }
            .addOnFailureListener { e ->
                Log.w(_TAG, "Error removing card from Firestore", e)
            }
    }


    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}
