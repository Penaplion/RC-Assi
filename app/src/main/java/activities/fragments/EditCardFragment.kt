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
import multipleroomtables.entities.Person
import multipleroomtables.entities.relations.PersonGroupCrossRef
import utils.CompareLists
import java.lang.NullPointerException

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

        groupInfo(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Database.getInstance(view.context).dao
        val imm: InputMethodManager =
            view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.btnCancel.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnFinish.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            if (!binding.rvPersonsInGroup.isEmpty() && binding.etGroupName.text.isNotBlank()) {
                if (args.groupID == 0) {
                    addGroup(view)
                } else {
                    editGroup(view)
                }
            }
            Navigation.findNavController(view).popBackStack()
        }

        binding.ibtnDelete.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            deleteGroup(view)

            Navigation.findNavController(view).popBackStack()
        }

        binding.btnAdd.setOnClickListener {
            if (binding.actvName.text.isNotBlank()) {
                val person: Person

                runBlocking {
                    person = db.getPersonByName(binding.actvName.text.toString())
                }
                try {
                    (binding.rvPersonsInGroup.adapter as EditCardListPersonAdapter).addItem(PersonItem(person.person_id, binding.actvName.text.toString()))
                } catch (e: NullPointerException) {
                    (binding.rvPersonsInGroup.adapter as EditCardListPersonAdapter).addItem(PersonItem(0, binding.actvName.text.toString()))
                }

                binding.actvName.text.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // show group information when group already exists
    private fun groupInfo(view: View): ArrayList<PersonItem> {
        val list = ArrayList<PersonItem>()
        if (args.groupID == 0) {
            binding.ibtnDelete.isEnabled = false
        } else {
            val db = Database.getInstance(view.context).dao
            runBlocking {
                db.getPersonsOfGroup(args.groupID).forEach {
                    binding.etGroupName.text =
                        Editable.Factory.getInstance().newEditable(it.group.groupName)
                    it.persons.forEach { it2 ->
                        list += PersonItem(it2.person_id, it2.person_name)
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
                if (it.person_id == 0) {
                    db.insertPerson(it)
                }
                personId += db.getPersonByName(it.person_name).person_id
            }

            //Add Person to Group
            personId.forEach {
                personGroupCrossRef += PersonGroupCrossRef(it, db.getGroups().last().group_id)
            }
            personGroupCrossRef.forEach { db.insertPersonGroupCrossRef(it) }
        }
    }

    private fun editGroup(view: View) {
        val db = Database.getInstance(view.context).dao
        val newPersons =
            (binding.rvPersonsInGroup.adapter as EditCardListPersonAdapter).getListOfPersons().toMutableList()
        val newGroup = Group(
            0,
            binding.etGroupName.text.toString(),
            binding.rvPersonsInGroup.size
        )

        runBlocking {
            val currentGroup = db.getGroupByID(args.groupID)
            var currentPersons = db.getPersonsOfGroup(currentGroup.group_id).first().persons.toMutableList()
            var personId: Int

            val bool = CompareLists().compareAllPersons(currentPersons, newPersons)
            if (!bool || (currentGroup.groupName != newGroup.groupName)) {
                // edit groupInfo
                db.updateGroup(currentGroup.group_id, newGroup.groupName, newGroup.memberCount)

                // add new Persons to Group and look if this person already exists in the Database
                newPersons.forEach {
                    if(!db.personsContain(it.person_name)) { // completely new
                        db.insertPerson(Person(0, it.person_name))
                        personId = db.getPersonByName(it.person_name).person_id
                        db.insertPersonGroupCrossRef(PersonGroupCrossRef(personId, currentGroup.group_id))
                    } else if (!db.groupContainsPerson(it.person_name, currentGroup.group_id)) { // person already exists in the database
                        personId = db.getPersonByName(it.person_name).person_id
                        db.insertPersonGroupCrossRef(PersonGroupCrossRef(personId, currentGroup.group_id))
                    }
                    currentPersons = CompareLists().deletePersonInList(currentPersons, it.person_name)
                }
                // delete remaining persons in currentPersons
                currentPersons.forEach {
                    db.deletePersonInGroup(it.person_id, currentGroup.group_id)
                }
            }
        }
    }

    private fun deleteGroup(view: View) {
        val db = Database.getInstance(view.context).dao

        runBlocking {
            db.deleteGroup(args.groupID)
            db.deletePersonGroupCrossRef(args.groupID)
        }
    }
}