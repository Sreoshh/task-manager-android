package com.example.taskmanager.data.dao;

import androidx.room.OnConflictStrategy;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanager.data.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    // 🔹 Insert single task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);

    // 🔹 Insert multiple tasks (for backend sync)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    // 🔹 Delete single task
    @Delete
    void delete(Task task);

    // 🔹 Update task
    @Update
    void update(Task task);

    // 🔹 Delete all tasks
    @Query("DELETE FROM task_table")
    void deleteAll();

    // 🔹 Read all tasks
    @Query("SELECT * FROM task_table ORDER BY id DESC")
    LiveData<List<Task>> getAllTasks();
}
