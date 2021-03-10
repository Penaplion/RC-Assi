package services.daos

import androidx.room.Dao
import androidx.room.Query
import entities.Claims

@Dao
interface ClaimsDao {
    @Query("SELECT * FROM claims")
    fun getAll(): List<Claims>
}