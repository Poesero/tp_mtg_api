package com.example.tp_mtg_api.model

class CardsRepo {

    private val cardsDS = CardsDataSource()

    suspend fun  getCards(name:String) : ArrayList<Card>{
        return cardsDS.getCards(name)
    }
}