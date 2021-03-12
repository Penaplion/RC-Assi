package multipleroomtables.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true)
    val group_id: Int,

    val groupName: String,
    val memberCount: Int
)