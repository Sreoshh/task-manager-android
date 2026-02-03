package com.example.taskmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.reminder.ReminderUtil
import com.example.taskmanager.viewmodel.TaskViewModel
import java.util.Calendar
import java.util.Date

class AddTaskActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private var selectedTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val editTitle = findViewById<EditText>(R.id.edit_text_title)
        val editDescription = findViewById<EditText>(R.id.edit_text_description)
        val buttonPickDate = findViewById<Button>(R.id.button_pick_date)
        val textDueDate = findViewById<TextView>(R.id.text_due_date)
        val editRepeatDays = findViewById<EditText>(R.id.edit_repeat_days)
        val buttonSave = findViewById<Button>(R.id.button_save_task)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        // 📅 Date & Time Picker
        buttonPickDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, year, month, day ->
                    TimePickerDialog(
                        this,
                        { _, hour, minute ->
                            calendar.set(year, month, day, hour, minute)
                            selectedTime = calendar.timeInMillis
                            textDueDate.text = Date(selectedTime).toString()
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                    ).show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // 💾 Save Task (UPDATED)
        buttonSave.setOnClickListener {
            val title = editTitle.text.toString().trim()
            val description = editDescription.text.toString().trim()
            val repeatDays = editRepeatDays.text.toString().toIntOrNull() ?: 0

            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = Task(
                title,
                description,
                selectedTime,
                selectedTime > 0,
                repeatDays
            )

            taskViewModel.insert(task)

            if (selectedTime > 0) {
                ReminderUtil.scheduleReminder(this, task)
            }

            finish()
        }
    }
}
