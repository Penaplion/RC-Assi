package activities.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentAssignArticlesToPersonsBinding
import com.example.rc_assi.databinding.FragmentGroupMenuBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import multipleroomtables.entities.Article
import multipleroomtables.entities.Receipt
import multipleroomtables.entities.relations.PersonArticleCrossRef
import viewModels.SharedGroupMenuViewModels
import kotlin.properties.Delegates

class AssignArticlesToPersonsFragment : Fragment() {
    private var _binding: FragmentAssignArticlesToPersonsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedGroupMenuViewModels by activityViewModels()
    private var receiptId by Delegates.notNull<Int>()

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val groupID = activity?.intent?.getIntExtra("GROUP_ID", 0)!!

        //DatenbankInstanz
        val db = Database.getInstance(view.context).dao



        binding.btnAdd.setOnClickListener {
            //Hinzufügen ohne Löschen des Geschriebenen
            val name: String = binding.etName.text.toString()
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
        }

        binding.btnAddAndClear.setOnClickListener {

            val name: String = binding.etName.text.toString()
            val price: Float = binding.etPrice.text.toString().toFloat()
            val amount: Float = binding.etAmount.text.toString().toFloat()
            val receiptnumber: Int = receiptId
            val unit: String = binding.spinner.selectedItem.toString()

            binding.etName.text = Editable.Factory.getInstance().newEditable("")
            binding.etPrice.text = Editable.Factory.getInstance().newEditable("")
            binding.etAmount.text = Editable.Factory.getInstance().newEditable("")

            runBlocking {
                db.insertArticle(Article(0, receiptnumber, price, amount, name, unit))
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