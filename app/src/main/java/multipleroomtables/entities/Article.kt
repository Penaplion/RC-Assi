package multipleroomtables.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article (
    @PrimaryKey(autoGenerate = true)
    val article_id: Int,
    val receipt_id: Int,

    val price: Float,
    val amount: Int,
    val name: String
)