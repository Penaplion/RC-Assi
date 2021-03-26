package activities.fragments

import adapters.ReceiptWithArticlesAdapter
import adapters.ShoppingHistoryAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentReceiptWithArticlesBinding
import com.example.rc_assi.databinding.FragmentShoppingHistoryBinding
import data.ArticleItem
import data.ReceiptItem
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database

class ReceiptWithArticlesFragment : Fragment() {

    private var _binding: FragmentReceiptWithArticlesBinding? = null
    private val binding get() = _binding!!
    // private val args: ReceiptWithArticlesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptWithArticlesBinding.inflate(inflater, container, false)
        val view = binding.root

        val db = Database.getInstance(view.context).dao
        val articleList: MutableList<ArticleItem> = emptyList<ArticleItem>().toMutableList()

        runBlocking {
            // TODO("get list<ArticleItem> and build RecyclerView content for that specific receipt")
            /*val articles = db.getReceiptWithArticles(args.receipt_id)[0].article
            articles.forEach {
                articleList += ArticleItem(
                    it.name,
                    it.price,
                    "unknown"
                )
            }*/
        }

        binding.rvArticles.adapter = ReceiptWithArticlesAdapter(articleList)
        binding.rvArticles.layoutManager = LinearLayoutManager(view.context)

        return view
    }


}