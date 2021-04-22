package multipleroomtables.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    val receipt_id: Int,
    val person_id: Int,
    val group_id: Int,  // added

    val date: Long,
    val market: String,
    val state: Boolean,
    val url: String,
    val total: Float
)