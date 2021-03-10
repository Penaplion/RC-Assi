package services.daos

import androidx.room.Dao
import androidx.room.Query
import entities.Expense

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense")
    fun getAll(): List<Expense>
}