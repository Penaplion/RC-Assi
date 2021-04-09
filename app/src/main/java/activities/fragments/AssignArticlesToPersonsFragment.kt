package activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentAssignArticlesToPersonsBinding
import com.example.rc_assi.databinding.FragmentGroupMenuBinding

class AssignArticlesToPersonsFragment : Fragment() {
    private var _binding: FragmentAssignArticlesToPersonsBinding? = null
    private val binding get() = _binding!!
    private val args: AssignArticlesToPersonsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAssignArticlesToPersonsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val groupID = activity?.intent?.getIntExtra("GROUP_ID", 0)!!


        binding.btnSave.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_assignArticlesToPersonsFragment_to_groupMenuFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}