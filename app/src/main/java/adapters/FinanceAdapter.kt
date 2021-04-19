package adapters

import activities.fragments.FinanceFragmentDirections
import activities.fragments.ShoppingHistoryFragmentDirections
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentFinancePersonBinding
import data.PersonItem
import viewModels.SharedGroupMenuViewModels

class FinanceAdapter(private val personList: ArrayList<PersonItem>, private val sharedViewModel: SharedGroupMenuViewModels) :
    RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FinanceViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_finance_person, parent, false)
        return FinanceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FinanceViewHolder, position: Int) {
        val currentItem = personList[position]
        with(holder) {
            binding.tvPerson.text = currentItem.name
            binding.cvPerson.setOnClickListener {
                sharedViewModel.setCreditor(binding.tvPerson.text.toString())
                val action = FinanceFragmentDirections.actionFinanceFragmentToFinanceFromPersonFragment()
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount() = personList.size

    class FinanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentFinancePersonBinding.bind(itemView)
    }
}