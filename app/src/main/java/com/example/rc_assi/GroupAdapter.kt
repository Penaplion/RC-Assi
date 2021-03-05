package com.example.rc_assi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.databinding.ActivityGroupItemBinding

class GroupAdapter(private val exampleList: List<GroupItem>) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_group_item, parent, false)
        return GroupViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val currentItem = exampleList[position]
        with(holder){
            binding.ivGroupPicture.setImageResource(currentItem.imageResource)
            binding.tvMembers.text = currentItem.members
            binding.tvGroupName.text = currentItem.groupName
        }
    }

    override fun getItemCount() = exampleList.size

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ActivityGroupItemBinding.bind(itemView)
    }
}