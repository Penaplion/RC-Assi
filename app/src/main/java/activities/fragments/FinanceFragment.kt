package activities.fragments

import adapters.FinanceAdapter
import adapters.GroupAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.R
import com.example.rc_assi.databinding.FragmentFinanceBinding
import com.example.rc_assi.databinding.FragmentGroupBinding
import data.GroupItem
import data.PersonItem
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import viewModels.SharedGroupMenuViewModels

class FinanceFragment : Fragment() {
    private val sharedViewModel: SharedGroupMenuViewModels by activityViewModels()
    private var _binding: FragmentFinanceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinanceBinding.inflate(inflater, container, false)
        val view = binding.root

        val db = Database.getInstance(view.context).dao
        val personList: MutableList<PersonItem> = emptyList<PersonItem>().toMutableList()

        runBlocking {
            val personFromDatabase = db.getPersonsOfGroup(sharedViewModel.groupId.value!!)[0]
            personFromDatabase.persons.forEach {
                personList += PersonItem(it.person_id, it.person_name)
            }
        }

        binding.rvFinancePerson.adapter = FinanceAdapter(personList as ArrayList<PersonItem>, sharedViewModel)
        binding.rvFinancePerson.layoutManager = LinearLayoutManager(requireContext())

        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}