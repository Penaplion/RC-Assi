package services.daos

import androidx.room.Dao
import androidx.room.Query
import entities.EndBalance

@Dao
interface EndBalanceDao {
    @Query("SELECT * FROM endbalance")
    fun getAll(): List<EndBalance>
}