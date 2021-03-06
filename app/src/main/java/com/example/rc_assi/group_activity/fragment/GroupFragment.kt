package com.example.rc_assi.group_activity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.group_activity.GroupAdapter
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentGroupBinding
import com.example.rc_assi.group_activity.GroupItem

class GroupFragment : Fragment() {

    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupBinding.inflate(inflater, container, false)

        val view = binding.root

        val exampleList = generateDummyList(7)

        binding.rvGroupCard.adapter =
            GroupAdapter(exampleList)
        binding.rvGroupCard.layoutManager = GridLayoutManager(view.context, 2,
                RecyclerView.VERTICAL, false)

        return view
    }

    private fun generateDummyList(size: Int): List<GroupItem> {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddCard.setOnClickListener {
            Navigation.findNavController(view).
                    navigate(R.id.action_groupFragment_to_editCardFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}