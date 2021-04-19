package activities.fragments

import adapters.FinancePersonAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentFinanceFromPersonBinding
import com.example.rc_assi.databinding.FragmentGroupMenuBinding
import data.DebtorArticleItem
import data.FinanceFromPersonItem
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import multipleroomtables.entities.Receipt
import multipleroomtables.entities.relations.GroupWithPersons
import viewModels.SharedGroupMenuViewModels

class FinanceFromPersonFragment : Fragment() {
    private var _binding: FragmentFinanceFromPersonBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedGroupMenuViewModels by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFinanceFromPersonBinding.inflate(inflater, container, false)
        val view = binding.root

        val db = Database.getInstance(requireContext()).dao
        val receipts: List<Receipt>
        val financeFromPersons: MutableList<FinanceFromPersonItem> = emptyList<FinanceFromPersonItem>().toMutableList()
        runBlocking {
            // get the creditor-ID
            val creditorId = db.getPersonIDByName(sharedViewModel.creditor.value!!)
            // get all receipts of the current group
            receipts = db.getGroupWithReceipts(sharedViewModel.groupId.value!!)[0].receipt
            receipts.forEach {
                // check if the current creditor owns the receipt
                if (it.person_id == creditorId) {
                    // get all articles of the current receipt
                    val articles = db.getReceiptWithArticles(it.receipt_id)[0].article
                    articles.forEach {
                        // find the debtor of the current article
                        val debtor = db.getPersonsOfArticle(it.article_id)[0].persons[0].person_name
                        // check if debtor is already in the list
                        if (!utils.CompareLists().containsDebtor(financeFromPersons, debtor)) {
                            financeFromPersons += FinanceFromPersonItem(debtor, emptyList())
                        }
                        // TODO("missing isChecked column in DB for article")
                        // add item to debtorArticleList
                        financeFromPersons[utils.CompareLists()
                            .findPosition(financeFromPersons, debtor)]
                            .list += DebtorArticleItem(it.name, it.price, false)
                        // this whole shit is inefficient AF
                        // but I won't change the database structure again...
                    }
                }
            }
        }
        // finished listBuilder that is needed for the nested recyclerView in 1h and 45min 🎉 🎉 ( ﾉ ﾟｰﾟ)ﾉ ＼(ﾟｰﾟ＼) ＼(ﾟｰﾟ＼) ( ﾉ ﾟｰﾟ)ﾉ

        // finished parent recylerView in 30min  (｡･∀･)ﾉﾞ

        // finished child recyclerView in another 30min (☞ﾟヮﾟ)☞ ☜(ﾟヮﾟ☜)

        binding.rvDebtor.adapter = FinancePersonAdapter(financeFromPersons as ArrayList<FinanceFromPersonItem>, sharedViewModel, requireContext())
        binding.rvDebtor.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}