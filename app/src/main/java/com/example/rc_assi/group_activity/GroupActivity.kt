package com.example.rc_assi.group_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rc_assi.databinding.ActivityGroupBinding

class GroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        }
}