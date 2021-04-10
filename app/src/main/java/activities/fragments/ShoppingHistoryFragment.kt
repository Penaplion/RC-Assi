package activities.fragments

import adapters.ShoppingHistoryAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rc_assi.databinding.FragmentShoppingHistoryBinding
import data.ReceiptItem
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import viewModels.SharedGroupMenuViewModels
import kotlin.properties.Delegates

class ShoppingHistoryFragment : Fragment() {

    private var _binding: FragmentShoppingHistoryBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedGroupMenuViewModels by activityViewModels()
    private var groupId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingHistoryBinding.inflate(inflater, container, false)

        sharedViewModel.groupId.observe(viewLifecycleOwner, {
            groupId = it
        })

        val view = binding.root

        val db = Database.getInstance(view.context).dao
        val receiptList: MutableList<ReceiptItem> = emptyList<ReceiptItem>().toMutableList()

        runBlocking {
            // TODO("Check later after filling DB with receipts by Queries")
            val receipts = db.getGroupWithReceipts(groupId)
            receipts.forEach {
//                receiptList += ReceiptItem(
//                    it.market,
//                    db.getPersonById(it.person_id).person_name,
//                    Timestamp(it.date).toString()
//                )
            }
        }

        binding.rvReceipts.adapter = ShoppingHistoryAdapter(receiptList, sharedViewModel)
        binding.rvReceipts.layoutManager = LinearLayoutManager(view.context)

        return view
    }
}