package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Receipt::class,
        parentColumns = arrayOf("receipt_id"),
        childColumns = arrayOf("receipt_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    val item_id: Int,
    val receipt_id: Int,
    @ColumnInfo(name = "price") val price: Float?,
    @ColumnInfo(name = "amount") val amount: Int?,
    @ColumnInfo(name = "name") val name: String?
)