package data
import android.content.Context
import model.Card

class CardsRepo {

    private val cardsDS = CardsDataSource()

    suspend fun  getCards(name:String, context: Context) : ArrayList<Card>{
        return cardsDS.getCards(name,context)
    }

    suspend fun getRandom(): Card {
        return cardsDS.getRandom()
    }

    suspend fun getCardsColors(name: String,color:String) : ArrayList<Card>{
        return cardsDS.getCardsColors(name,color)
    }
}