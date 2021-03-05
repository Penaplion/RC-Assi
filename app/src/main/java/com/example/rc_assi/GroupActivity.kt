package com.example.rc_assi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_assi.databinding.ActivityGroupBinding


class GroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val exampleList = generateDummyList(3)
        binding.rvGroupCards.adapter = GroupAdapter(exampleList)
        binding.rvGroupCards.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        }

    private fun generateDummyList(size: Int): List<GroupItem> {
        val list = ArrayList<GroupItem>()

        for (i in 0 until size) {
            val item = GroupItem(R.drawable.ic_launcher_background, "Group Name $i", "Members $i")
            list += item
        }

        return list
    }
}