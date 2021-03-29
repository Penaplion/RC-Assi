package data

data class ReceiptItem(
    val receipt_id: Int,
    val person_id: Int,
    val group_id: Int,  // added

    val date: Long,
    val market: String,
    val state: Boolean,
    val total: Float,

    val owner: String
)