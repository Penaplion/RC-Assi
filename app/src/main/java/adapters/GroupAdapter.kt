package adapters

import activities.fragments.GroupFragmentDirections
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentGroupItemBinding
import data.GroupItem
import multipleroomtables.Database

class GroupAdapter(private val groupList: List<GroupItem>) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_group_item, parent, false)
        return GroupViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val currentItem = groupList[position]
        with(holder) {
            binding.ivGroupPicture.setImageResource(currentItem.imageResource)
            binding.tvMembers.text = currentItem.members
            binding.tvGroupName.text = currentItem.groupName
            // Navigate to another Fragment
            binding.ibtnEdit.setOnClickListener {
                val action = GroupFragmentDirections.actionGroupFragmentToEditCardFragment()
                action.groupIndex = (position + 1)
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount() = groupList.size

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentGroupItemBinding.bind(itemView)
    }
}