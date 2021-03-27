package activities.fragments

import activities.GroupActivity
import activities.GroupMenuActivity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentGroupBinding
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
         val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackToGroup.setOnClickListener {
            val intent = Intent(
                context?.applicationContext,
                GroupActivity::class.java
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.applicationContext?.startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}