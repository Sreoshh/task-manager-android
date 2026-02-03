package com.example.taskmanager.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.taskmanager.data.entity.Task;

public class ReminderUtil {

    public static void scheduleReminder(Context context, Task task) {

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("title", task.getTitle());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int) System.currentTimeMillis(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (task.getRepeatIntervalDays() > 0) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    task.getDueDateTime(),
                    task.getRepeatIntervalDays() * AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        } else {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    task.getDueDateTime(),
                    pendingIntent
            );
        }
    }
}
