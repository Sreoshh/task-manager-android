package com.example.taskmanager

import android.view.View
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.adapter.TaskAdapter
import com.example.taskmanager.viewmodel.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerView setup
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = TaskAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            this.adapter = adapter
        }

        // ViewModel
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        // Observe data
        val emptyText = findViewById<TextView>(R.id.text_empty)

        taskViewModel.getAllTasks().observe(this) { tasks ->

            if (tasks.isEmpty()) {
                emptyText.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyText.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                adapter.setTasks(tasks)
            }
        }

        // Checkbox update
        adapter.setOnTaskCheckedListener { task ->
            taskViewModel.update(task)
        }

        // Swipe to delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = adapter.getTaskAt(viewHolder.adapterPosition)
                taskViewModel.delete(task)
                Toast.makeText(this@MainActivity, "Task deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)

        // FAB
        findViewById<FloatingActionButton>(R.id.fab_add_task).setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }
}