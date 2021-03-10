package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.*

@Entity(
    primaryKeys = ["person_id", "group_id"],
    foreignKeys = [ForeignKey(
        entity = Person::class,
        parentColumns = arrayOf("person_id"),
        childColumns = arrayOf("person_id"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Group::class,
        parentColumns = arrayOf("group_id"),
        childColumns = arrayOf("group_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Expense(
    val person_id: Int,
    val group_id: Int,
    @ColumnInfo(name = "date") val timestamp: Date?,
    @ColumnInfo(name = "sum") val sum: Float?
)