package activities.fragments

import adapters.AssignmentsParentAdapter
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentAssignArticlesToPersonsBinding
import data.ChildAssignmentsItem
import data.NestedAsignmentsItem
import utils.CompareLists
import viewModels.SharedAssignArticlesToPersonsParentViewModel
import com.example.rc_assi.databinding.FragmentGroupMenuBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import multipleroomtables.entities.Article
import multipleroomtables.entities.Receipt
import multipleroomtables.entities.relations.PersonArticleCrossRef
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

        val db = Database.getInstance(requireContext()).dao

        var arraySpinnerPerson = emptyArray<String>()

        runBlocking {
            val persons = db.getPersonsOfGroup(sharedViewModel.groupId.value!!)[0]
            persons.persons.forEach {
                arraySpinnerPerson += it.person_name
            }
        }

        val adapterPerson: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arraySpinnerPerson
        )
        adapterPerson.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAssignPerson.adapter = adapterPerson

        val arraySpinner: Array<String> = arrayOf("Stk.", "kg", "g")

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arraySpinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

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
            val receiptnumber: Int = receiptId
            val unit: String = binding.spinner.selectedItem.toString()

            lifecycleScope.launch {
                db.insertArticle(Article(0, receiptnumber, price, amount, name, unit))
                val person_id = db.getPersonByName(binding.spinnerAssignPerson.selectedItem.toString()).person_id
                val article_id = db.getArticles().last().article_id
                db.insertPersonArticleCrossRef(PersonArticleCrossRef(person_id, article_id))
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
            val receiptnumber: Int = receiptId
            val unit: String = binding.spinner.selectedItem.toString()

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
            Navigation.findNavController(requireView()).popBackStack(R.id.groupMenuFragment, false)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}