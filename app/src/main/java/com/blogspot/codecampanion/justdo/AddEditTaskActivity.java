package com.blogspot.codecampanion.justdo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditTaskActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_TASK = "EXTRA_TASK";
    public static final String EXTRA_SUBTASK = "EXTRA_SUBTASK";


    private EditText taskText, subTaskText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        taskText = findViewById(R.id.task);
        subTaskText = findViewById(R.id.subTask);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Task");
            taskText.setText(intent.getStringExtra(EXTRA_TASK));
            subTaskText.setText(intent.getStringExtra(EXTRA_SUBTASK));
        } else {
            setTitle("Add Task");
        }

    }

    private void saveItem() {
        String task = taskText.getText().toString();
        String subTask = subTaskText.getText().toString();

        if (task.trim().isEmpty() || subTask.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a Task and a SubTask", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TASK, task);
        data.putExtra(EXTRA_SUBTASK, subTask);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.save_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveTask:
                saveItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
