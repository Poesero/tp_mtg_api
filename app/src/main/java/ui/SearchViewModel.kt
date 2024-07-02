package ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import data.CardsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newSingleThreadContext
import model.Card
import kotlin.coroutines.CoroutineContext

class SearchViewModel : ViewModel() {
    private val _TAG = "API-CHECK"
    private val cardsRepo: CardsRepo = CardsRepo()
    private val coroutineContext: CoroutineContext = newSingleThreadContext("card")
    private val scope = CoroutineScope(coroutineContext)
    private var card: MutableLiveData<Card> = MutableLiveData<Card>()
            var cards: MutableLiveData<ArrayList<Card>> = MutableLiveData<ArrayList<Card>>()

    fun init(name: String){
        scope.launch{
            kotlin.runCatching {
                cardsRepo.getCards(name)
            }.onSuccess {
                Log.d(_TAG,"Cards on success: ${cards.value} ")
                cards.postValue(it)
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
}