package multipleroomtables.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense (
    @PrimaryKey(autoGenerate = true)
    val expense_id: Int,

    val person_id: Int,
    val group_id: Int
)