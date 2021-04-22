package data

data class FinanceFromPersonItem (
    val debtor: String,
    var list: List<DebtorArticleItem>
)