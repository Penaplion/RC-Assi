package com.example.rc_assi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.intellij.lang.annotations.JdkConstants

class GroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val exampleList = generateDummyList(3)

        val recyclerView: RecyclerView = findViewById(R.id.rvGroupCards)
        recyclerView.adapter = GroupAdapter(exampleList)
        recyclerView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
    }

    private fun generateDummyList(size: Int): List<GroupExampleItem> {
        val list = ArrayList<GroupExampleItem>()

        for (i in 0 until size) {
            val item = GroupExampleItem(R.drawable.ic_launcher_background, "Group Name $i", "Members $i")
            list += item
        }

        return list
    }
}