package activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.rc_assi.databinding.FragmentAddReceiptBinding
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import multipleroomtables.entities.Receipt
import viewModels.SharedGroupMenuViewModels
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class AddReceiptFragment : Fragment() {

    private var _binding: FragmentAddReceiptBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedGroupMenuViewModels by activityViewModels()
    private var groupId by Delegates.notNull<Int>()
    //private var imageUrl by Delegates.notNull<String>()
    private var imageUrl: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddReceiptBinding.inflate(inflater, container, false)
        groupId = sharedViewModel.groupId.value!!
        sharedViewModel.groupId.observe(viewLifecycleOwner, {
            groupId = it
        })
        sharedViewModel.imageUrl.observe(viewLifecycleOwner, {
            imageUrl = it
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val db = Database.getInstance(view.context).dao

        var arraySpinner = emptyArray<String>()

        runBlocking {
            val persons = db.getPersonsOfGroup(groupId)[0]
            persons.persons.forEach {
                arraySpinner += it.person_name
            }
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arraySpinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOwner.adapter = adapter

        binding.btnGoToDist.setOnClickListener {
            val timestamp = Timestamp(binding.cvDatePicker.year-1900, binding.cvDatePicker.month, binding.cvDatePicker.dayOfMonth, 0, 0, 0, 0)
            val date: Long = timestamp.time
            val market: String = binding.etMarket.text.toString()
            val status: Boolean = false
            val total = binding.etTotal.text.toString().replace(',', '.').toFloat()
            val personName: String = binding.spinnerOwner.selectedItem as String
            val url: String = imageUrl
            val groupid: Int = groupId
            runBlocking {
                val personID = db.getPersonIDByName(personName)
                db.insertReceipt(Receipt(0, personID, groupid, date, market, status, url, total))
                sharedViewModel.setReceiptId(db.getReceipts().last().receipt_id)
            }

            val action =
                AddReceiptFragmentDirections.actionAddReceiptFragmentToAssignArticlesToPersonsFragment()
            Navigation.findNavController(view).navigate(action)
        }
        binding.ibtnTakePhoto.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(AddReceiptFragmentDirections.actionAddReceiptFragmentToCameraFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}