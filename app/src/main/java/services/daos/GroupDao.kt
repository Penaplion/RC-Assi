package services.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import entities.Group

@Dao
interface GroupDao {
    @Query("SELECT * FROM 'group'")
    fun getAll(): List<Group>

    @Query("SELECT * FROM 'group' WHERE group_id = :groupID")
    fun getGroupById(groupID: Int): List<Group>

    /*
    @Query("INSERT INTO 'group'(name, mitgliederanzahl) VALUES(:groupName, :countGroupMembers) ")
    fun insertGroup(groupName: String, countGroupMembers: Int): String{
        return "erfolgreich gespeichert"
    }
     */

    @Insert
    fun insert(group: Group)
}