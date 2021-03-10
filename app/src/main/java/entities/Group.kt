package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true)
    val group_id: Int?,
    @ColumnInfo(name = "name") val groupName: String,
    @ColumnInfo(name = "mitgliederanzahl") val memberCount: Int
)