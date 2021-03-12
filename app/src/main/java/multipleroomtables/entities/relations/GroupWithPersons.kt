package multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import multipleroomtables.entities.Group
import multipleroomtables.entities.Person

data class GroupWithPersons (
    @Embedded val group: Group,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "person_id",
        associateBy = Junction(PersonGroupCrossRef::class)
    )
    val persons: List<Person>
)