package com.example.taskmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.taskmanager.data.dao.TaskDao;
import com.example.taskmanager.data.database.TaskDatabase;
import com.example.taskmanager.data.entity.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskViewModel extends AndroidViewModel {

    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public TaskViewModel(@NonNull Application application) {
        super(application);
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        executorService.execute(() -> taskDao.insert(task));
    }

    public void delete(Task task) {
        executorService.execute(() -> taskDao.delete(task));
    }
}
