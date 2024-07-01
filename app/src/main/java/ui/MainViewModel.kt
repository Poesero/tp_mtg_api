package ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.Card
import data.CardsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {

    //constante
    private val _TAG_ = "API-CHECK"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("")
    private val scope = CoroutineScope(coroutineContext)
    //Dependencias
    private val cardsRepo = CardsRepo()
    //Propiedades
    var cards = MutableLiveData<ArrayList<Card>>()
    var name = ""

    //Funciones
    fun onStart(){
        scope.launch {
            kotlin.runCatching {
                cardsRepo.getCards(name)
            }.onSuccess {
                Log.d(_TAG_,"Cards on success ")
                cards.postValue(it)
            }.onFailure {
                Log.e(_TAG_,"Cards error: " + it)
            }
        }
    }

}