package activities.fragments

import adapters.EditCardListPersonAdapter
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isEmpty
import androidx.core.view.size
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rc_assi.databinding.FragmentEditCardBinding
import data.PersonItem
import kotlinx.coroutines.launch
import multipleroomtables.Database
import multipleroomtables.entities.Group
import multipleroomtables.entities.Person
import multipleroomtables.entities.relations.PersonGroupCrossRef

class EditCardFragment : Fragment() {

    private var _binding: FragmentEditCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditCardBinding.inflate(inflater, container, false)
        val view = binding.root
        /*
        TODO("SQL Query fehlt noch. " +
                "In der Relation 'IsIn' stehen alle Personen in den jeweiligen Gruppen drin. " +
                "Ergo müssen für eine existente Gruppe alle bereits enthaltenen Mitgleider hier in die Liste gefüllt werden.")
         */
        val personList = ArrayList<PersonItem>()

        binding.rvPersonsInGroup.adapter = EditCardListPersonAdapter(personList)
        binding.rvPersonsInGroup.layoutManager = LinearLayoutManager(view.context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnFinish.setOnClickListener {
            addGroup(view)
        }

        binding.ibtnDelete.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnAdd.setOnClickListener {
            if (binding.actvName.text.isNotBlank()) {
                (binding.rvPersonsInGroup.adapter as EditCardListPersonAdapter).addItem(binding.actvName.text.toString())
                binding.actvName.text.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ButtonLogic
    private fun addGroup (view: View) {
        if (!binding.rvPersonsInGroup.isEmpty() && binding.etGroupName.text.isNotBlank()) {
            val db = Database.getInstance(view.context).dao
            val persons = (binding.rvPersonsInGroup.adapter as EditCardListPersonAdapter).getListOfPersons()
            val personGroupCrossRef: MutableList<PersonGroupCrossRef> = emptyList<PersonGroupCrossRef>().toMutableList()
            val personId: MutableList<Int> = emptyList<Int>().toMutableList()

            lifecycleScope.launch {
                // Add Group to Database
                db.insertGroup(Group(0, binding.etGroupName.text.toString(), binding.rvPersonsInGroup.size))

                // Add Persons to Database
                persons.forEach {
                    if (db.isInTablePersons(it.person_name) == 0) {
                        db.insertPerson(it)
                    }
                    personId += db.getPerson(it.person_name).person_id
                }

                //Add Person to Group
                personId.forEach {
                    personGroupCrossRef += PersonGroupCrossRef(it, db.getGroups().last().group_id)
                }
                personGroupCrossRef.forEach {db.insertPersonGroupCrossRef(it)}

                // Jump back to previous fragment
                Navigation.findNavController(view).popBackStack()
            }
        }
    }
}