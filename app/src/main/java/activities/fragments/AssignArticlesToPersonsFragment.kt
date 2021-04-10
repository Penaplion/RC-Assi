package activities.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentAssignArticlesToPersonsBinding
import com.example.rc_assi.databinding.FragmentGroupMenuBinding
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import multipleroomtables.entities.Article
import multipleroomtables.entities.Receipt
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

            runBlocking {
                db.insertArticle(Article(0, receiptnumber, price, amount, name))
            }
        }

        binding.btnAddAndClear.setOnClickListener {

            val name: String = binding.etName.text.toString()
            val price: Float = binding.etPrice.text.toString().toFloat()
            val amount: Float = binding.etAmount.text.toString().toFloat()
            val receiptnumber: Int = receiptId

            binding.etName.text = Editable.Factory.getInstance().newEditable("")
            binding.etPrice.text = Editable.Factory.getInstance().newEditable("")
            binding.etAmount.text = Editable.Factory.getInstance().newEditable("")

            runBlocking {
                db.insertArticle(Article(0, receiptnumber, price, amount, name))
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