package data.dbLocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [CardLocal::class, FavoriteCard::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardsDAO
    abstract fun favoriteCardsDao(): FavoritesCardsDAO

    companion object {
        @Volatile
        private var _instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = _instance ?: synchronized(this) {
            _instance ?: buildDatabase(context).also { _instance = it }
        }

        private fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(
            context, AppDatabase::class.java, "app_database_demo"
        ).fallbackToDestructiveMigration().build()

        fun clean(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context).clearAllTables()
            }
        }
    }
}
