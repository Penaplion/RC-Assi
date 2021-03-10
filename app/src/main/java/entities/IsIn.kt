package entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["person_id","group_id"],
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
data class IsIn(
    val person_id: Int,
    val group_id: Int
)