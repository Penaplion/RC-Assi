package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentFinanceFromPersonItemListitemBinding
import data.DebtorArticleItem
import viewModels.SharedGroupMenuViewModels

class FinanceArticleAdapter(private val articleList: ArrayList<DebtorArticleItem>, private val sharedViewModel: SharedGroupMenuViewModels) :
    RecyclerView.Adapter<FinanceArticleAdapter.FinanceArticleViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FinanceArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_finance_from_person_item_listitem, parent, false)
        return FinanceArticleAdapter.FinanceArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: FinanceArticleViewHolder,
        position: Int
    ) {
        val currentItem = articleList[position]
        with(holder) {
            binding.tvassignedArticle.text = currentItem.article
            binding.tvArticlePrice.text = currentItem.price.toString()
        }
    }

    override fun getItemCount() = articleList.size

    class FinanceArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = FragmentFinanceFromPersonItemListitemBinding.bind(itemView)
    }
}