package multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import multipleroomtables.entities.Person
import multipleroomtables.entities.Receipt

data class PersonWithReceipts (
    @Embedded val person: Person,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "person_id"
    )
    val receipts: List<Receipt>
)