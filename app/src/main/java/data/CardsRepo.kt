package data

import model.Card

class CardsRepo {

    private val cardsDS = CardsDataSource()

    suspend fun  getCards(name:String) : ArrayList<Card>{
        return cardsDS.getCards(name)
    }

    suspend fun getRandom(): Card {
        return cardsDS.getRandom()
    }

    suspend fun getCardsColors(name: String,color:String) : ArrayList<Card>{
        return cardsDS.getCardsColors(name,color)
    }
}