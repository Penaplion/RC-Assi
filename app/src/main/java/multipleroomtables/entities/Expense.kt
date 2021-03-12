package multipleroomtables.entities

import androidx.room.Entity

@Entity(primaryKeys = ["person_id", "group_id"])
data class Expense (
    val person_id: Int,
    val group_id: Int
)