package multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import multipleroomtables.entities.Group
import multipleroomtables.entities.Receipt

data class GroupWithReceipts (
    @Embedded val group: Group,
    @Relation(
        parentColumn = "group_id",
        entityColumn = "group_id"
    )
    val receipt: List<Receipt>
)