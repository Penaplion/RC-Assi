package activities.fragments

import adapters.AssignmentsParentAdapter
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentAssignArticlesToPersonsBinding
import data.ChildAssignmentsItem
import data.NestedAsignmentsItem
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import multipleroomtables.entities.Article
import multipleroomtables.entities.relations.PersonArticleCrossRef
import utils.CompareLists
import viewModels.SharedAssignArticlesToPersonsParentViewModel
import viewModels.SharedGroupMenuViewModels
import java.lang.IndexOutOfBoundsException
import kotlin.properties.Delegates


class AssignArticlesToPersonsFragment : Fragment() {
    private var _binding: FragmentAssignArticlesToPersonsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedGroupMenuViewModels by activityViewModels()
    private var receiptId by Delegates.notNull<Int>()
    private var nestedList = ArrayList<NestedAsignmentsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAssignArticlesToPersonsBinding.inflate(inflater, container, false)

        receiptId = sharedViewModel.receiptId.value!!
        sharedViewModel.receiptId.observe(viewLifecycleOwner, {
            receiptId = it
        })

        binding.rvParentRecyclerView.adapter = AssignmentsParentAdapter(nestedList, requireContext())
        binding.rvParentRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //DatenbankInstanz
        val db = Database.getInstance(view.context).dao

        binding.btnAdd.setOnClickListener {
            //Hinzufügen ohne Löschen des Geschriebenen
            val articleName: String = binding.etName.text.toString()
            val nameOfPersonSharing = binding.etNameOfPersonSharing.text.toString()
            val price: Float = binding.etPrice.text.toString().toFloat()
            val amount: Float = binding.etAmount.text.toString().toFloat()
            val receiptNumber: Int = receiptId

            runBlocking {
                val articleId: Long =
                    db.insertArticle(Article(0, receiptNumber, price, amount, articleName))
                if (nameOfPersonSharing.isNotBlank()) {
                    val personId: Int = db.getPersonIDByName(nameOfPersonSharing)
                    val crossRef = PersonArticleCrossRef(personId, articleId.toInt())
                    db.insertPersonArticleCrossRef(crossRef)
                }
            }

            try {
                nestedList[CompareLists().getIndexFromListAssignments(nestedList, nameOfPersonSharing)].list.add(
                    ChildAssignmentsItem(articleName, price)
                )
            } catch (e: IndexOutOfBoundsException) {
                val childList: ArrayList<ChildAssignmentsItem> = ArrayList()
                childList.add(ChildAssignmentsItem(articleName, price))
                nestedList.add(NestedAsignmentsItem(nameOfPersonSharing, childList))
            }
            binding.rvParentRecyclerView.adapter?.notifyDataSetChanged()

        }

        binding.btnAddAndClear.setOnClickListener {

            val name: String = binding.etName.text.toString()
            val nameOfPersonSharing = binding.etNameOfPersonSharing.text.toString()
            val price: Float = binding.etPrice.text.toString().toFloat()
            val amount: Float = binding.etAmount.text.toString().toFloat()
            val receiptNumber: Int = receiptId

            binding.etName.text = Editable.Factory.getInstance().newEditable("")
            binding.etPrice.text = Editable.Factory.getInstance().newEditable("")
            binding.etAmount.text = Editable.Factory.getInstance().newEditable("")
            binding.etNameOfPersonSharing.text = Editable.Factory.getInstance().newEditable("")

            runBlocking {
                val articleId: Long =
                    db.insertArticle(Article(0, receiptNumber, price, amount, name))
                if (nameOfPersonSharing.isNotBlank()) {
                    val personId: Int = db.getPersonIDByName(nameOfPersonSharing)
                    val crossRef = PersonArticleCrossRef(personId, articleId.toInt())
                    db.insertPersonArticleCrossRef(crossRef)
                }
            }
        }


        //Hinzufügen mit Löschen des Geschriebenen

        binding.btnSave.setOnClickListener {
            //Speichern der Quittung
            // TODO("popBackStack to GroupMenuFragment")
            Navigation.findNavController(requireView()).popBackStack(R.id.groupMenuFragment, true)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}