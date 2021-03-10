package services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import entities.*
import services.daos.*
import utils.Converters


@Database(
    entities = arrayOf(
        Person::class,
        Group::class,
        Item::class,
        EndBalance::class,
        Receipt::class,
        Claims::class,
        IsIn::class,
        Expense::class
    ), version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun groupDao(): GroupDao
    abstract fun itemDao(): ItemDao
    abstract fun endBalanceDao(): EndBalanceDao
    abstract fun receiptDao(): ReceiptDao
    abstract fun claimsDao(): ClaimsDao
    abstract fun isInDao(): IsInDao
    abstract fun expenseDao(): ExpenseDao

    companion object {

        @Volatile
        private var INSTANCE: services.AppDatabase? = null
        private val sLock = Any()
        open fun getInstance(context: Context): services.AppDatabase? {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase::class.java, "Database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE
            }
        }
    }

}