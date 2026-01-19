package com.example.taskmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.viewmodel.TaskViewModel
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = TaskAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        taskViewModel.getAllTasks().observe(this) { tasks ->
            adapter.setTasks(tasks)
        }
        val fab = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(
            R.id.fab_add_task
        )

        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

}
