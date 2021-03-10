package activities.fragments

import adapters.EditCardListPersonAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.view.size
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rc_assi.databinding.FragmentEditCardBinding
import data.PersonItem
import entities.Group
import services.AppDatabase


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
        val db = AppDatabase.getInstance(view.context)

        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnFinish.setOnClickListener {
            // Navigation.findNavController(view).popBackStack()

            if (!binding.rvPersonsInGroup.isEmpty() && !binding.etGroupName.text.isBlank()) {
                // füge die Personen und die Gruppe in die DB ein
                db?.personDao()?.insertAll((binding.rvPersonsInGroup.adapter as EditCardListPersonAdapter).getPersonList())
                db?.groupDao()?.insert(Group(null, binding.etGroupName.text.toString(), binding.rvPersonsInGroup.size))


            }
        }

        binding.ibtnDelete.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnAdd.setOnClickListener {
            if (!binding.actvName.text.isBlank()) {
                (binding.rvPersonsInGroup.adapter as EditCardListPersonAdapter).addItem(binding.actvName.text.toString())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}