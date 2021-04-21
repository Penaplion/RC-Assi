package adapters

import com.example.rc_assi.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.databinding.FragmentAssignArticlesToPersonsChildItemBinding
import data.ChildAssignmentsItem
import viewModels.SharedAssignArticlesToPersonsChildViewModel

class AssignmentsChildAdapter(
    private val childModelArrayList: ArrayList<ChildAssignmentsItem>
) :
    RecyclerView.Adapter<AssignmentsChildAdapter.MyViewHolder>() {
    
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentAssignArticlesToPersonsChildItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_assign_articles_to_persons_child_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = childModelArrayList[position]
        with(holder) {
            binding.tvAssignmentItemArticleName.text = currentItem.article
            binding.tvAssignmentItemArticlePrice.text = currentItem.price.toString()
        }
    }

    override fun getItemCount(): Int {
        return childModelArrayList.size
    }
}