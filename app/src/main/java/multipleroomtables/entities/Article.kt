package multipleroomtables.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article (
    @PrimaryKey(autoGenerate = true)
    val article_id: Int,
    val receipt_id: Int,

    val price: Float,
    val amount: Float,
    val name: String,
    val unit: String
)