package ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun addFavorite(userId: String, cardName: String, context: Context) {
        val db = AppDatabase.getInstance(context)
        db.favoriteCardsDao().addFavorite(FavoriteCard(cardName, userId))
    }

    fun removeFavorite(userId: String, cardName: String, context: Context) {
        val db = AppDatabase.getInstance(context)
        val favoriteCard = db.favoriteCardsDao().isFavorite(userId, cardName)
        favoriteCard?.let {
            db.favoriteCardsDao().removeFavorite(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}
