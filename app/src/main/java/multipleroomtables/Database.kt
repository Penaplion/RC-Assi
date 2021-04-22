package multipleroomtables

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import multipleroomtables.entities.*
import multipleroomtables.entities.relations.PersonArticleCrossRef
import multipleroomtables.entities.relations.PersonGroupCrossRef

@Database(
    entities = [
        Article::class,
        EndBalance::class,
        Group::class,
        Person::class,
        Receipt::class,
        PersonArticleCrossRef::class,
        PersonGroupCrossRef::class
    ],
    version = 2
)
abstract class Database : RoomDatabase() {

    abstract val dao: Dao

    companion object {
        @Volatile
        private var INSTANCE: multipleroomtables.Database? = null

        fun getInstance(context: Context): multipleroomtables.Database {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    multipleroomtables.Database::class.java,
                    "database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build().also {
                    INSTANCE = it
                }
            }
        }
    }
}