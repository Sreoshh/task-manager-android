<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:padding="16dp">

<EditText
android:id="@+id/edit_text_title"
android:layout_width="0dp"
android:layout_height="wrap_content"
android:hint="Task title"
android:inputType="text"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintEnd_toEndOf="parent"/>

<EditText
android:id="@+id/edit_text_description"
android:layout_width="0dp"
android:layout_height="wrap_content"
android:hint="Task description"
android:inputType="textMultiLine"
android:layout_marginTop="12dp"
app:layout_constraintTop_toBottomOf="@id/edit_text_title"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintEnd_toEndOf="parent"/>

<Button
android:id="@+id/button_save_task"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Save"
android:layout_marginTop="24dp"
app:layout_constraintTop_toBottomOf="@id/edit_text_description"
app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
