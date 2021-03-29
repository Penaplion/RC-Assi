package adapters

import activities.fragments.ShoppingHistoryFragmentDirections
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentShoppingHistoryItemBinding
import data.ReceiptItem

class ShoppingHistoryAdapter(private val receiptList: List<ReceiptItem>) :
    RecyclerView.Adapter<ShoppingHistoryAdapter.ShoppingHistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingHistoryHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_shopping_history_item, parent, false)
        return ShoppingHistoryHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ShoppingHistoryHolder, position: Int) {
        val currentItem = receiptList[position]
        with(holder) {
            binding.tvOwner.text = currentItem.owner
            binding.tvShop.text = currentItem.market
            binding.tvTimestamp.text = currentItem.date.toString()
            binding.cvReceipt.setOnClickListener {
                val action = ShoppingHistoryFragmentDirections.actionShoppingHistoryFragmentToReceiptWithArticlesFragment()
                action.receiptID = currentItem.receipt_id
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount() = receiptList.size

    class ShoppingHistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentShoppingHistoryItemBinding.bind(itemView)
    }
}