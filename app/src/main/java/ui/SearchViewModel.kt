package ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import data.CardsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newSingleThreadContext
import model.Card
import model.CardImages
import kotlin.coroutines.CoroutineContext

class SearchViewModel : ViewModel() {
    private val cardsRepo: CardsRepo = CardsRepo()
    private val coroutineContext: CoroutineContext = newSingleThreadContext("card")
    private val scope = CoroutineScope(coroutineContext)
            var card: MutableLiveData<Card> = MutableLiveData<Card>()
            var cards: MutableLiveData<ArrayList<Card>> = MutableLiveData<ArrayList<Card>>()
    fun init(id: String){
        scope.launch{
            kotlin.runCatching {
                cardsRepo.getCards(id)
            }.onSuccess {
                card.postValue((it ?: Card()) as Card?)
            }.onFailure {
                val carb = Card()
                carb.name = "Error."
                card.postValue(Card())
            }
        }

    }
}