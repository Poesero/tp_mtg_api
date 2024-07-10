package data
import android.content.Context
import model.Card

class CardsRepo {


    suspend fun getCards(name: String, context: Context): ArrayList<Card> {
        return CardsDataSource.getCards(name, context)
    }

    suspend fun getRandom(): Card {
        return CardsDataSource.getRandom()
    }

    suspend fun getCardsColors(name: String, color: String): ArrayList<Card> {
        return CardsDataSource.getCardsColors(name, color)
    }

    /*
    suspend fun getCard(name: String) : Card?{
        return CardsDataSource.getCard(name)
    }
    */
}