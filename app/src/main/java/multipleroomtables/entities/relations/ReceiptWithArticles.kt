package multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import multipleroomtables.entities.Article
import multipleroomtables.entities.Receipt

data class ReceiptWithArticles (
    @Embedded val receipt: Receipt,
    @Relation(
        parentColumn = "receipt_id",
        entityColumn = "receipt_id"
    )
    val article: List<Article>
)