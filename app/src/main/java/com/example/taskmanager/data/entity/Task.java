package com.example.taskmanager.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private boolean completed;
    private long dueDateTime;
    private boolean reminderEnabled;
    private int repeatIntervalDays;


    public Task(String title, String description, long dueDateTime,
                boolean reminderEnabled, int repeatIntervalDays) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.dueDateTime = dueDateTime;
        this.reminderEnabled = reminderEnabled;
        this.repeatIntervalDays = repeatIntervalDays;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getDueDateTime() {
        return dueDateTime;
    }

    public boolean isReminderEnabled() {
        return reminderEnabled;
    }

    public int getRepeatIntervalDays() {
        return repeatIntervalDays;
    }
}
