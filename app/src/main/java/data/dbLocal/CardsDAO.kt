package data.dbLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CardsDAO {

    @Query("SELECT * FROM cards")
    fun getAll() : List<CardLocal>


    @Query("SELECT * FROM cards WHERE name LIKE '%' || :name || '%'")
    fun getBySubstring(name: String): List<CardLocal>

    @Query("SELECT * FROM cards WHERE name = :name LIMIT 1")
    fun getByPK(name: String):CardLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg card: CardLocal)

    @Delete
    fun delete(card: CardLocal)
}

