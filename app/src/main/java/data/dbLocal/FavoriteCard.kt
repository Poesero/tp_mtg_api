package data.dbLocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cards")
data class FavoriteCard(
    val name: String,
     @PrimaryKey val userId: String
)
