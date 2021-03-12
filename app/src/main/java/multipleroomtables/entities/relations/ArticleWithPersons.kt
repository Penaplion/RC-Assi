package multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import multipleroomtables.entities.Article
import multipleroomtables.entities.Person

data class ArticleWithPersons (
    @Embedded val article: Article,
    @Relation(
        parentColumn = "article_id",
        entityColumn = "person_id",
        associateBy = Junction(PersonArticleCrossRef::class)
    )
    val persons: List<Person>
)