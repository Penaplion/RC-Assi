package activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentAddReceiptBinding
import data.ReceiptItem

class AddReceiptFragment : Fragment() {

    private var _binding: FragmentAddReceiptBinding? = null
    private val binding get() = _binding!!
    private val args: AddReceiptFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentAddReceiptBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoToDist.setOnClickListener {
            // save receipt in db

            // var newReceipt: ReceiptItem = ReceiptItem(0,)
            // var receiptID: Int = 0

            /*
                val receipt_id: Int,
                val person_id: Int,
                val group_id: Int,  // added

                val date: Long,
                val market: String,
                val url: String,
                val state: Boolean,
                val total: Float,
                val owner: String
             */


            val action =
                AddReceiptFragmentDirections.actionAddReceiptFragmentToAssignArticlesToPersonsFragment()
            // action.receiptID = receiptID
            Navigation.findNavController(view).navigate(action)
        }
        binding.ibtnTakePhoto.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_addReceiptFragment_to_cameraFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}