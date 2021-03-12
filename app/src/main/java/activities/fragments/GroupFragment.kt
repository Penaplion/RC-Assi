package activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import adapters.GroupAdapter
import androidx.lifecycle.lifecycleScope
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentGroupBinding
import data.GroupItem
import kotlinx.coroutines.launch
import multipleroomtables.Database

class GroupFragment : Fragment() {
    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupBinding.inflate(inflater, container, false)

        val view = binding.root

        // create rvGroupCard content
        lifecycleScope.launch {
            val groupsFromDatabase = Database.getInstance(view.context).dao.getGroups()
            val groupList: MutableList<GroupItem> = emptyList<GroupItem>().toMutableList()

            groupsFromDatabase.forEach {
                val stringMember = getString(R.string.member)
                val amount = it.memberCount.toString()
                groupList += GroupItem(R.drawable.ic_launcher_background,it.groupName, "$stringMember $amount")
            }

            binding.rvGroupCard.adapter =
                GroupAdapter(groupList)
            binding.rvGroupCard.layoutManager = GridLayoutManager(view.context, 2,
                RecyclerView.VERTICAL, false)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddCard.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_groupFragment_to_editCardFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}