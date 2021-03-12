package multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import multipleroomtables.entities.Article
import multipleroomtables.entities.Person

data class PersonWithArticles (
    @Embedded val person: Person,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "article_id",
        associateBy = Junction(PersonArticleCrossRef::class)
    )
    val articles: List<Article>
)