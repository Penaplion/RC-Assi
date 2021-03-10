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
import androidx.lifecycle.LiveData
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentGroupBinding
import data.GroupItem
import entities.Group
import services.AppDatabase

class GroupFragment : Fragment() {
    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupBinding.inflate(inflater, container, false)

        //val groupItems: List<GroupItem> = emptyList()
        //val view = binding.root

        val view = binding.root
        val groupList = AppDatabase.getInstance(view.context)?.groupDao()?.getAll()
        var groupItems: List<GroupItem> = emptyList()

        groupList?.forEach {
            val memberString = getString(R.string.groupMembersCount)
            val memberCount = it.memberCount.toString()
            groupItems += GroupItem(R.drawable.ic_launcher_background,it.groupName, "$memberString $memberCount")
        }

        binding.rvGroupCard.adapter =
                GroupAdapter(groupItems)
        binding.rvGroupCard.layoutManager = GridLayoutManager(view.context, 2,
                RecyclerView.VERTICAL, false)

        return view
    }

    /*
    private fun generateDummyList(size: Int): ArrayList<GroupItem> {
        val list = ArrayList<GroupItem>()

        for (i in 0 until size) {
            val item = GroupItem(
                    R.drawable.ic_launcher_background,
                    "Group Name $i",
                    "Members $i"
            )
            list += item
        }

        return list
    }

     */

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