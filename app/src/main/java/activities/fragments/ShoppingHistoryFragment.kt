package activities.fragments

import adapters.ShoppingHistoryAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rc_assi.databinding.FragmentShoppingHistoryBinding
import data.ReceiptItem
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database

class ShoppingHistoryFragment : Fragment() {

    private var _binding: FragmentShoppingHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingHistoryBinding.inflate(inflater, container, false)
        val view = binding.root


        val db = Database.getInstance(view.context).dao
        val receiptList: MutableList<ReceiptItem> = emptyList<ReceiptItem>().toMutableList()

        runBlocking {
            // TODO("get list<ReceiptItem> and build RecyclerView content for that specific group")
        }

        binding.rvReceipts.adapter = ShoppingHistoryAdapter(receiptList)
        binding.rvReceipts.layoutManager = LinearLayoutManager(view.context)

        return view
    }
}