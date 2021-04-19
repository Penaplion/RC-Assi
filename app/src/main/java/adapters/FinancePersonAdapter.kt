package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentFinanceFromPersonItemBinding
import data.DebtorArticleItem
import data.FinanceFromPersonItem
import viewModels.SharedGroupMenuViewModels

class FinancePersonAdapter(private val debtorList: ArrayList<FinanceFromPersonItem>, private val sharedViewModel: SharedGroupMenuViewModels, private val context: Context) :
    RecyclerView.Adapter<FinancePersonAdapter.FinancePersonViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FinancePersonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_finance_from_person_item, parent, false)
        return FinancePersonViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: FinancePersonViewHolder,
        position: Int
    ) {
        val currentItem = debtorList[position]
        with(holder) {
            binding.tvPersonItem.text = currentItem.debtor
            binding.rvArticleList.adapter = FinanceArticleAdapter(currentItem.list as ArrayList<DebtorArticleItem>, sharedViewModel)
            binding.rvArticleList.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getItemCount() = debtorList.size

    class FinancePersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = FragmentFinanceFromPersonItemBinding.bind(itemView)
    }
}