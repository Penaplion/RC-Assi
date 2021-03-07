package com.example.rc_assi.group_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentGroupItemBinding

class GroupAdapter(private val exampleList: List<GroupItem>) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_group_item, parent, false)
        return GroupViewHolder(
                itemView
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val currentItem = exampleList[position]
        with(holder) {
            binding.ivGroupPicture.setImageResource(currentItem.imageResource)
            binding.tvMembers.text = currentItem.members
            binding.tvGroupName.text = currentItem.groupName
            // Navigate to another Fragment
            binding.ibtnEdit.setOnClickListener {
                Navigation.findNavController(holder.itemView).navigate(R.id.action_groupFragment_to_editCardFragment)
            }
        }
    }

    override fun getItemCount() = exampleList.size

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentGroupItemBinding.bind(itemView)
    }
}