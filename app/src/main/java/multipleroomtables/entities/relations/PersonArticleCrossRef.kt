package multipleroomtables.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["person_id", "article_id"])
data class PersonArticleCrossRef (
    val person_id: Int,
    val article_id: Int
)