package services.daos

import androidx.room.Dao
import androidx.room.Query
import entities.IsIn

@Dao
interface IsInDao {
    @Query("SELECT * FROM isin")
    fun getAll(): List<IsIn>
}