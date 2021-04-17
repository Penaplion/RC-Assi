package activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentGroupMenuBinding
import viewModels.SharedGroupMenuViewModels
import kotlin.properties.Delegates


class GroupMenuFragment : Fragment() {

    private var _binding: FragmentGroupMenuBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedGroupMenuViewModels by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGroupMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val groupID = activity?.intent?.getIntExtra("GROUP_ID", 0)!!
        sharedViewModel.setGroupId(groupID)

        binding.cvPurchases.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(GroupMenuFragmentDirections.actionGroupMenuFragmentToShoppingHistoryFragment())
        }
        binding.cvCamera.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(GroupMenuFragmentDirections.actionGroupMenuFragmentToAddReceiptFragment())
        }
        binding.btnBackToGroup.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}