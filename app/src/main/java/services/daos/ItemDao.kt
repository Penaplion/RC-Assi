package services.daos

import androidx.room.Dao
import androidx.room.Query
import entities.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): List<Item>
}