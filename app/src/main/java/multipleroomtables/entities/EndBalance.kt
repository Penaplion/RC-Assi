package multipleroomtables.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EndBalance(
    @PrimaryKey(autoGenerate = true)
    val balance_id: Int,
    val group_id: Int,

    val creditor: Int,
    val sum: Float,
    val debtor: Int
)