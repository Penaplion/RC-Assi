package entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Person::class,
        parentColumns = arrayOf("person_id"),
        childColumns = arrayOf("person_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    val receipt_id: Int,
    val person_id: Int,
    @ColumnInfo(name = "price") val total: Float?,
    @ColumnInfo(name = "status") val status: Boolean?,
    @ColumnInfo(name = "market") val market: String?,
    @ColumnInfo(name = "date") val date: Date?
)