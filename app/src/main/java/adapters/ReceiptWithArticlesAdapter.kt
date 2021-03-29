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
            binding.tvArticle.text = currentItem.name
            binding.tvAssignments.text = currentItem.assignment
            binding.tvPrice.text = currentItem.price.toString() + R.string.currency_symbol
        }
    }

    override fun getItemCount() = articleList.size

    class ReceiptWithArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentReceiptWithArticlesItemBinding.bind(itemView)
    }
}