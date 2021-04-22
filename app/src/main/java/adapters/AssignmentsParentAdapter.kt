package adapters

import android.content.Context
import com.example.rc_assi.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.databinding.FragmentAssignArticlesToPersonsItemBinding
import data.NestedAsignmentsItem
import viewModels.SharedAssignArticlesToPersonsChildViewModel
import viewModels.SharedAssignArticlesToPersonsParentViewModel

class AssignmentsParentAdapter(
    private val exampleList: ArrayList<NestedAsignmentsItem>,
    private val context: Context
) :
    RecyclerView.Adapter<AssignmentsParentAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentAssignArticlesToPersonsItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_assign_articles_to_persons_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.binding.tvOwner.text = currentItem.owner
        holder.binding.rvChildRecyclerView.adapter = AssignmentsChildAdapter(currentItem.list)
        holder.binding.rvChildRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun notifyChanges() {
        notifyDataSetChanged()
    }
}