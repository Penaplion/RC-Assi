package activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentGroupMenuBinding


class GroupMenuFragment : Fragment() {

    private var _binding :FragmentGroupMenuBinding? = null
    private val binding get() = _binding!!

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

        binding.cvPurchases.setOnClickListener {
            val action = GroupMenuFragmentDirections.actionGroupMenuFragmentToShoppingHistoryFragment()
            action.groupID = groupID
            Navigation.findNavController(view).navigate(action)
        }
        binding.cvCamera.setOnClickListener {
           Navigation.findNavController(view).navigate(R.id.action_groupMenuFragment_to_addReceiptFragment)
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