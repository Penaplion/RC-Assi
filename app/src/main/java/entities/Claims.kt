package entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["person_id", "item_id"],
    foreignKeys = [ForeignKey(
        entity = Person::class,
        parentColumns = arrayOf("person_id"),
        childColumns = arrayOf("person_id"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Item::class,
        parentColumns = arrayOf("item_id"),
        childColumns = arrayOf("item_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Claims(
    val person_id: Int,
    val item_id: Int
)