package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            binding.tvShop.text = currentItem.shop
            binding.tvTimestamp.text = currentItem.shop
            binding.cvReceipt.setOnClickListener {
                TODO("navigate to article list")
            }
        }
    }

    override fun getItemCount() = receiptList.size

    class ShoppingHistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentShoppingHistoryItemBinding.bind(itemView)
    }
}