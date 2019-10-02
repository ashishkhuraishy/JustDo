package com.blogspot.codecampanion.justdo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private ToDoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setHasFixedSize(true);

                final ToDoAdapter adapter = new ToDoAdapter();
                recyclerView.setAdapter(adapter);

                viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
                viewModel.getData().observe(this, new Observer<List<ToDo>>() {
                    @Override
                    public void onChanged(List<ToDo> toDos) {
                        //Update Recycler View
                adapter.setTasks(toDos);
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.fabButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE && resultCode == AddTaskActivity.RESULT_OK){
            String task = data.getStringExtra(AddTaskActivity.EXTRA_TASK);
            String subTask = data.getStringExtra(AddTaskActivity.EXTRA_SUBTASK);

            ToDo toDo = new ToDo(task, subTask);
            viewModel.insert(toDo);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
