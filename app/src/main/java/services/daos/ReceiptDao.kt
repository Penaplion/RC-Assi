package services.daos

import androidx.room.Dao
import androidx.room.Query
import entities.Receipt

@Dao
interface ReceiptDao {
    @Query("SELECT * FROM receipt")
    fun getAll(): List<Receipt>
}