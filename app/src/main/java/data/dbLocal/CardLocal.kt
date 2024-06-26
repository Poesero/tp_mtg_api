package data.dbLocal

import androidx.room.Entity
import model.CardImages

@Entity (tableName = "cards")
data class CardLocal(
    var obj: String? = null,
    var mana_cost: String? = null,
    var name: String? = null,
    var power: String? = null,
    var toughness: String? = null,
    var type_line: String? = null,
    var oracle_text: String? = null,
    var image_uris: CardImages? = null
)