package services.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import entities.Person

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun getAll(): LiveData<List<Person>>

    @Insert
    fun insertAll(persons: ArrayList<Person>)
    /*
    @Query("INSERT INTO 'Person'(name) VALUES(:persons) ")
    fun insertAllPersons(vararg persons: Person): String{
        return "erfolgreich gespeichert"
    }
     */
    @Delete
    fun delete(person: Person)
}