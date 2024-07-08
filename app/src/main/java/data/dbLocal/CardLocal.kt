package data.dbLocal

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "cards")
data class CardLocal(
    var obj: String? = null,
    @PrimaryKey var name: String,
    var type_line: String? = null,
    var oracle_text: String? = null,

)