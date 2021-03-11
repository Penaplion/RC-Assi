package multipleroomtables.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Receipt (
    @PrimaryKey(autoGenerate = true)
    val receipt_id: Int,
    val person_id: Int,

    val date: Long,
    val market: String,
    val state: Boolean,
    val total: Float
)