package multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import multipleroomtables.entities.EndBalance
import multipleroomtables.entities.Group

data class GroupAndEndBalance (
    @Embedded val group: Group,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "group_id"
    )
    val endBalance: EndBalance
)