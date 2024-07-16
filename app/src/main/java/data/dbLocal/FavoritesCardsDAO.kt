package data.dbLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesCardsDAO {

    @Query("SELECT * FROM favorite_cards WHERE userId = :userId")
    fun getAllFavorites(userId: String): List<FavoriteCard>

    @Query("SELECT * FROM favorite_cards WHERE userId = :userId AND name = :name LIMIT 1")
    fun isFavorite(userId: String, name: String): FavoriteCard?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favoriteCard: FavoriteCard)

    @Delete
    fun removeFavorite(favoriteCard: FavoriteCard)
}
