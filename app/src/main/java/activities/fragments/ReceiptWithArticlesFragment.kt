package activities.fragments

import adapters.ReceiptWithArticlesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rc_assi.databinding.FragmentReceiptWithArticlesBinding
import data.ArticleItem
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import viewModels.SharedGroupMenuViewModels
import kotlin.properties.Delegates

class ReceiptWithArticlesFragment : Fragment() {

    private var _binding: FragmentReceiptWithArticlesBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedGroupMenuViewModels by activityViewModels()
    private var receiptId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptWithArticlesBinding.inflate(inflater, container, false)
        val view = binding.root

        receiptId = sharedViewModel.receiptId.value!!
        sharedViewModel.receiptId.observe(viewLifecycleOwner, {
            receiptId = it
        })

        val db = Database.getInstance(view.context).dao
        val articleList: MutableList<ArticleItem> = emptyList<ArticleItem>().toMutableList()

        runBlocking {
            val articles = db.getReceiptWithArticles(receiptId)[0].article
            articles.forEach {
                articleList += ArticleItem(
                    it.article_id,
                    it.receipt_id,
                    it.price,
                    it.amount,
                    it.name,
                    it.unit,
                    "unknown" // TODO("missing")
                )
            }
        }

        binding.rvArticles.adapter = ReceiptWithArticlesAdapter(articleList)
        binding.rvArticles.layoutManager = LinearLayoutManager(view.context)

        return view
    }


}