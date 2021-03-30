package data

data class ArticleItem (
    val article_id: Int,
    val receipt_id: Int,

    val price: Float,
    val amount: Int,
    val name: String,

    val assignment: String
)