package data
import android.content.Context
import model.Card

class CardsRepo {


    fun getCards(name: String, context: Context): ArrayList<Card> {
        return CardsDataSource.getCards(name, context)
    }

    fun getRandom(): Card? {
        return CardsDataSource.getRandom()
    }

    fun getCardsColors(name: String, color: String): ArrayList<Card> {
        return CardsDataSource.getCardsColors(name, color)
    }



}