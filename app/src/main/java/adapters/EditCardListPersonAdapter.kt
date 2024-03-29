package adapters

import data.PersonItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentEditCardListPersonItemBinding
import multipleroomtables.entities.Person

class EditCardListPersonAdapter(private val personList: ArrayList<PersonItem>) :
    RecyclerView.Adapter<EditCardListPersonAdapter.EditCardListPersonViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditCardListPersonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_edit_card_list_person_item, parent, false)
        return EditCardListPersonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EditCardListPersonViewHolder, position: Int) {
        val currentItem = personList[position]
        with(holder) {
            binding.tvName.text = currentItem.name
            binding.ibtnRemovePerson.setOnClickListener {
                removeItem(position)
            }
        }
    }

    override fun getItemCount() = personList.size

    class EditCardListPersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FragmentEditCardListPersonItemBinding.bind(itemView)
    }

    private fun removeItem(position: Int) {
        personList.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(person: PersonItem) {
        personList.add(person)
        notifyDataSetChanged()
    }

    fun getListOfPersons(): List<Person> {
        val list: MutableList<Person> = emptyList<Person>().toMutableList()
        personList.forEach() {
            list += Person(it.person_id, it.name)
        }
        return list
    }
}
