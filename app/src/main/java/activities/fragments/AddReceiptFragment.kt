package activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentAddReceiptBinding
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import multipleroomtables.entities.Receipt


class AddReceiptFragment : Fragment() {

    private var _binding: FragmentAddReceiptBinding? = null
    private val binding get() = _binding!!
    private val args: AddReceiptFragmentArgs by navArgs()
    //private val args1: AddReceiptFragmentArgs by


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddReceiptBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val db = Database.getInstance(view.context).dao

        var arraySpinner = emptyArray<String>()




        runBlocking {
            val persons = db.getPersonsOfGroup(args.groupId)[0]
            persons.persons.forEach {
                arraySpinner+=it.person_name
            }

        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arraySpinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOwner.adapter = adapter




        binding.btnGoToDist.setOnClickListener {
                val date : Long = binding.cvDatePicker.date
                val market : String = binding.etMarket.toString()
                val status : Boolean = false
                val total : Float = binding.etTotal.toString().toFloat()
                val personName : String = binding.spinnerOwner.toString()
                val url : String =args.url
                val groupid : Int = args.groupId
              runBlocking {
                val personID =db.getPersonIDByName(personName)
                  db.insertReceipt(Receipt(0,personID,groupid,date,market,status,url,total))
              }


            val action =
                AddReceiptFragmentDirections.actionAddReceiptFragmentToAssignArticlesToPersonsFragment()
            //action.receiptID = receiptID
            Navigation.findNavController(view).navigate(action)
        }
        binding.ibtnTakePhoto.setOnClickListener {
            val action = AddReceiptFragmentDirections.actionAddReceiptFragmentToCameraFragment()
           action.groupId = args.groupId
            Navigation.findNavController(view)
                .navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}