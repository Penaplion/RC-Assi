package multipleroomtables.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["person_id", "group_id"])
data class PersonGroupCrossRef (
    val person_id: Int,
    val group_id: Int
)