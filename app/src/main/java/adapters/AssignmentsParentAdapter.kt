package adapters

import android.content.Context
import com.example.rc_assi.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.databinding.FragmentAssignArticlesToPersonsItemBinding
import viewModels.SharedAssignArticlesToPersonsChildViewModel
import viewModels.SharedAssignArticlesToPersonsParentViewModel

class AssignmentsParentAdapter(
    exampleList: ArrayList<SharedAssignArticlesToPersonsParentViewModel>,
    context: Context
) :
    RecyclerView.Adapter<AssignmentsParentAdapter.MyViewHolder>() {
    private val parentModelArrayList: ArrayList<SharedAssignArticlesToPersonsParentViewModel> =
        exampleList
    private var cxt: Context = context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentAssignArticlesToPersonsItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_assign_articles_to_persons_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parentModelArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = parentModelArrayList[position]
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.tvOwner.text = currentItem.owner()
        holder.binding.rvChildRecyclerView.layoutManager = layoutManager
        holder.binding.rvChildRecyclerView.setHasFixedSize(true)

        val arrayList: ArrayList<SharedAssignArticlesToPersonsChildViewModel> = ArrayList()

        // added the first child row
        if (parentModelArrayList[position].owner() == "heidi") {
            arrayList.add(SharedAssignArticlesToPersonsChildViewModel("schoki", 0.99))
            arrayList.add(SharedAssignArticlesToPersonsChildViewModel("wein", 8.99))
            arrayList.add(SharedAssignArticlesToPersonsChildViewModel("schoki", 0.99))
            arrayList.add(SharedAssignArticlesToPersonsChildViewModel("wein", 8.99))
        }

        val childRecyclerViewAdapter = AssignmentsChildAdapter(arrayList)
        holder.binding.rvChildRecyclerView.adapter = childRecyclerViewAdapter
    }
}