package com.example.tp_mtg_api.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp_mtg_api.model.Card
import com.example.tp_mtg_api.model.CardsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {

    //constante
    private val _TAG = "API-CHECK"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("Path")
    private val scope = CoroutineScope(coroutineContext)
    //Dependencias
    private val cardsRepo = CardsRepo()
    //Propiedades
    var cards = MutableLiveData<ArrayList<Card>>()
    var name = "Fatal"

    //Funciones
    fun onStart(){
        scope.launch {
            kotlin.runCatching {
                cardsRepo.getCards(name)
            }.onSuccess {
                Log.d(_TAG,"Cards on success ")
                cards.postValue(it)
            }.onFailure {
                Log.e(_TAG,"Cards error: " + it)
            }
        }
    }

}