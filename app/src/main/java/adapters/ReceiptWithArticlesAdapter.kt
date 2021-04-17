package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentReceiptWithArticlesItemBinding
import data.ArticleItem

class ReceiptWithArticlesAdapter(private val articleList: List<ArticleItem>) :
    RecyclerView.Adapter<ReceiptWithArticlesAdapter.ReceiptWithArticlesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptWithArticlesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_receipt_with_articles_item, parent, false)
        return ReceiptWithArticlesViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(
        holder: ReceiptWithArticlesViewHolder,
        position: Int
    ) {
        val currentItem = articleList[position]
        with(holder) {
            if (currentItem.unit == "Stk.") {
                binding.tvArticle.text = "%.0f".format(currentItem.amount) + "x " + currentItem.name
            } else if (currentItem.unit == "kg") {
                binding.tvArticle.text = "%.0f".format(currentItem.amount) + "kg " + currentItem.name
            } else if (currentItem.unit == "g") {
                binding.tvArticle.text = "%.0f".format(currentItem.amount) + "g " + currentItem.name
            }

            binding.tvAssignments.text = currentItem.assignment
            val price = "%.2f".format(currentItem.price * currentItem.amount)
            binding.tvPrice.text = price + holder.itemView.context.getString(R.string.currency_symbol)
        }
    }

    override fun getItemCount() = articleList.size

    class ReceiptWithArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentReceiptWithArticlesItemBinding.bind(itemView)
    }
}