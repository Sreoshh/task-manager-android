package com.example.taskmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.R;
import com.example.taskmanager.data.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private List<Task> tasks = new ArrayList<>();

    // 🔹 Listener for checkbox changes
    public interface OnTaskCheckedListener {
        void onTaskChecked(Task task);
    }

    private OnTaskCheckedListener onTaskCheckedListener;

    public void setOnTaskCheckedListener(OnTaskCheckedListener listener) {
        this.onTaskCheckedListener = listener;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task currentTask = tasks.get(position);

        holder.title.setText(currentTask.getTitle());
        holder.description.setText(currentTask.getDescription());

        // Avoid triggering listener when recycling views
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(currentTask.isCompleted());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTask.setCompleted(isChecked);
            if (onTaskCheckedListener != null) {
                onTaskCheckedListener.onTaskChecked(currentTask);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    // 🔹 Needed for swipe-to-delete
    public Task getTaskAt(int position) {
        return tasks.get(position);
    }

    static class TaskHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final CheckBox checkBox;

        public TaskHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            description = itemView.findViewById(R.id.text_description);
            checkBox = itemView.findViewById(R.id.checkbox_completed);
        }
    }
}
