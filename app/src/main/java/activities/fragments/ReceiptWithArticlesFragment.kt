package activities.fragments

import adapters.ReceiptWithArticlesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rc_assi.databinding.FragmentReceiptWithArticlesBinding
import data.ArticleItem
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database

class ReceiptWithArticlesFragment : Fragment() {

    private var _binding: FragmentReceiptWithArticlesBinding? = null
    private val binding get() = _binding!!
    private val args: ReceiptWithArticlesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptWithArticlesBinding.inflate(inflater, container, false)
        val view = binding.root

        val db = Database.getInstance(view.context).dao
        val articleList: MutableList<ArticleItem> = emptyList<ArticleItem>().toMutableList()

        runBlocking {
            val articles = db.getReceiptWithArticles(args.receiptID)[0].article
            articles.forEach {
                articleList += ArticleItem(
                    it.article_id,
                    it.receipt_id,
                    it.price,
                    it.amount,
                    it.name,
                    "unknown" // TODO("get assigned persons")
                )
            }
        }

        binding.rvArticles.adapter = ReceiptWithArticlesAdapter(articleList)
        binding.rvArticles.layoutManager = LinearLayoutManager(view.context)

        return view
    }


}