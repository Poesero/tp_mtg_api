package data.dbLocal


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [CardLocal::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardsDAO   

    companion object{
        @Volatile
        private var _instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = _instance ?: synchronized(this) {
          _instance ?: buildDatabase(context)
        }

        private fun buildDatabase(context: Context) : AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app_database_demo")
            .fallbackToDestructiveMigration()
            .build()

        suspend fun clean(context: Context) = coroutineScope {
            launch(Dispatchers.IO) {
                getInstance(context).clearAllTables()
            }
        }

    }
}

