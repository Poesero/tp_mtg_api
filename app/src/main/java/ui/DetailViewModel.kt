package ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.CardsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import model.Card
import kotlin.coroutines.CoroutineContext

class DetailViewModel  : ViewModel() {
    private val _TAG = "API-CHECK"
    private val cardsRepo: CardsRepo = CardsRepo()
    private val coroutineContext: CoroutineContext = newSingleThreadContext("card")
    private val scope = CoroutineScope(coroutineContext)
            var card: MutableLiveData<Card> = MutableLiveData<Card>()


    fun init(name: String, context: Context) {
        scope.launch{
            kotlin.runCatching {
                cardsRepo.getCards(name,context)
            }.onSuccess {
                Log.d(_TAG,"Cards on success: ${card.value} ")
                card.postValue(card.value)
                if (it.isNotEmpty()) {
                    card.postValue(it[0])
                }
            }.onFailure {
                val carb = Card(name = "ERROR")
                carb.name = "Error."
                card.postValue(Card(name = "ERROR"))
            }
        }

    }

    fun fetchRandomCard() {
        scope.launch {
            kotlin.runCatching {
                cardsRepo.getRandom()
            }.onSuccess {
                card.postValue(it)
            }.onFailure { e ->
                Log.e(_TAG, "Error fetching random card: ${e.message}")
                card.postValue(Card(name = "Error fetching random card"))
            }
        }
    }
}
