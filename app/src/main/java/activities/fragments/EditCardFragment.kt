package activities.fragments

import adapters.EditCardListPersonAdapter
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isEmpty
import androidx.core.view.size
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rc_assi.databinding.FragmentEditCardBinding
import data.PersonItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import multipleroomtables.Database
import multipleroomtables.entities.Group
import multipleroomtables.entities.relations.PersonGroupCrossRef

class EditCardFragment : Fragment() {

    private var _binding: FragmentEditCardBinding? = null
    private val binding get() = _binding!!
    private val args: EditCardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCardBinding.inflate(inflater, container, false)
        val view = binding.root

        groupInfo(args.groupIndex, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imm: InputMethodManager =
            view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.btnCancel.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnFinish.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
//            TODO("implement functions to edit a group")
            addGroup(view)

            Navigation.findNavController(view).popBackStack()
        }

        binding.ibtnDelete.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            deleteGroup(view)

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

    // show group information when group already exists
    private fun groupInfo(groupIndex: Int, view: View): ArrayList<PersonItem> {
        val list = ArrayList<PersonItem>()
        if (groupIndex == 0) {
            binding.ibtnDelete.isEnabled = false
        } else {
            val db = Database.getInstance(view.context).dao
            runBlocking {
                val groupId = db.getGroups()[groupIndex - 1].group_id
                db.getPersonsOfGroup(groupId).forEach {
                    binding.etGroupName.text =
                        Editable.Factory.getInstance().newEditable(it.group.groupName)
                    it.persons.forEach { it2 ->
                        list += PersonItem(it2.person_name)
                    }
                }
            }
        }

        binding.rvPersonsInGroup.adapter = EditCardListPersonAdapter(list)
        binding.rvPersonsInGroup.layoutManager = LinearLayoutManager(view.context)

        return list
    }

    // ButtonLogic
    private fun addGroup(view: View) {
        if (!binding.rvPersonsInGroup.isEmpty() && binding.etGroupName.text.isNotBlank()) {
            val db = Database.getInstance(view.context).dao
            val persons =
                (binding.rvPersonsInGroup.adapter as EditCardListPersonAdapter).getListOfPersons()
            val personGroupCrossRef: MutableList<PersonGroupCrossRef> =
                emptyList<PersonGroupCrossRef>().toMutableList()
            val personId: MutableList<Int> = emptyList<Int>().toMutableList()

            GlobalScope.launch {
                // Add Group to Database
                db.insertGroup(
                    Group(
                        0,
                        binding.etGroupName.text.toString(),
                        binding.rvPersonsInGroup.size
                    )
                )

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
                personGroupCrossRef.forEach { db.insertPersonGroupCrossRef(it) }
            }
        }
    }

    private fun editGroup(view: View) {
        if (!binding.rvPersonsInGroup.isEmpty() && binding.etGroupName.text.isNotBlank()) {
            val db = Database.getInstance(view.context).dao
            val persons =
                (binding.rvPersonsInGroup.adapter as EditCardListPersonAdapter).getListOfPersons()
            val personGroupCrossRef: MutableList<PersonGroupCrossRef> =
                emptyList<PersonGroupCrossRef>().toMutableList()
            val personId: MutableList<Int> = emptyList<Int>().toMutableList()

            lifecycleScope.launch {
                // Add Group to Database
                db.insertGroup(
                    Group(
                        0,
                        binding.etGroupName.text.toString(),
                        binding.rvPersonsInGroup.size
                    )
                )

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
                personGroupCrossRef.forEach { db.insertPersonGroupCrossRef(it) }

                // Jump back to previous fragment
                Navigation.findNavController(view).popBackStack()
            }
        }
    }

    private fun deleteGroup(view: View) {
        val db = Database.getInstance(view.context).dao

        GlobalScope.launch {
            val groupId = db.getGroups()[args.groupIndex - 1].group_id
            db.deleteGroup(groupId)
            db.deletePersonGroupCrossRef(groupId)
        }
    }
}