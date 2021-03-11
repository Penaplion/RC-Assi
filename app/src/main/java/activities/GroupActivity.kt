package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.rc_assi.databinding.ActivityGroupBinding
import kotlinx.coroutines.launch
import multipleroomtables.Database
import multipleroomtables.entities.*
import multipleroomtables.entities.relations.PersonArticleCrossRef
import multipleroomtables.entities.relations.PersonGroupCrossRef
import java.sql.Timestamp
import java.util.*

class GroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}