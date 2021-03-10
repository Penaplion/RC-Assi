package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Group::class,
        parentColumns = arrayOf("group_id"),
        childColumns = arrayOf("group_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class EndBalance(
    @PrimaryKey(autoGenerate = true)
    val balance_id: Int,
    val group_id: Int,
    @ColumnInfo(name = "creditor") val creditor: String?,
    @ColumnInfo(name = "sum") val sum: Float?,
    @ColumnInfo(name = "debtor") val debtor: String?
)