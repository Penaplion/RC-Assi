package data

data class ArticleItem (
    val article_id: Int,
    val receipt_id: Int,

    val price: Float,
    val amount: Float,
    val name: String,
    val unit: String,

    val assignment: String
)