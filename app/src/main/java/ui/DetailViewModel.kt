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

    fun init(name: String, context: Context) {
        coroutineScope.launch {
            kotlin.runCatching {
                cardsRepo.getCards(name, context)
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
}
