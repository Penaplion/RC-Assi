package multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import multipleroomtables.entities.Group
import multipleroomtables.entities.Person

data class PersonWithGroups (
    @Embedded val person: Person,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "group_id",
        associateBy = Junction(PersonGroupCrossRef::class)
    )
    val groups: List<Group>
)