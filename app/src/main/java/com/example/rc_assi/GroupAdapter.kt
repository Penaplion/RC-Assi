package com.example.rc_assi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class GroupAdapter(private val exampleList: List<GroupExampleItem>) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivGroupPicture: ImageView = itemView.findViewById(R.id.ivGroupPicture) // synthetic property?
        val tvGroupName: TextView = itemView.findViewById(R.id.tvGroupName)
        val tvMembers: TextView = itemView.findViewById(R.id.tvMembers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_group_item, parent, false)
        return GroupViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.ivGroupPicture.setImageResource(currentItem.imageResource)
        holder.tvMembers.text = currentItem.members
        holder.tvGroupName.text = currentItem.groupName
    }
}