package com.blogspot.codecampanion.justdo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_REQUEST_CODE = 1;
    private static final int EDIT_REQUEST_CODE = 2;

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
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getToDoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted Task", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setListener(new ToDoAdapter.SetOnItemClickListener() {
            @Override
            public void OnItemClick(ToDo toDo) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                intent.putExtra(AddEditTaskActivity.EXTRA_ID, toDo.getId());
                intent.putExtra(AddEditTaskActivity.EXTRA_TASK, toDo.getTask());
                intent.putExtra(AddEditTaskActivity.EXTRA_SUBTASK, toDo.getSubtask());

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == ADD_REQUEST_CODE && resultCode == AddEditTaskActivity.RESULT_OK) {
            String task = data.getStringExtra(AddEditTaskActivity.EXTRA_TASK);
            String subTask = data.getStringExtra(AddEditTaskActivity.EXTRA_SUBTASK);

            ToDo toDo = new ToDo(task, subTask);
            viewModel.insert(toDo);
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            String task = data.getStringExtra(AddEditTaskActivity.EXTRA_TASK);
            String subTask = data.getStringExtra(AddEditTaskActivity.EXTRA_SUBTASK);

            ToDo toDo = new ToDo(task, subTask);
            int id = data.getIntExtra(AddEditTaskActivity.EXTRA_ID, -1);
            if (id != -1) {
                toDo.setId(id);
                viewModel.update(toDo);
                Toast.makeText(this, "Update Sucess", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Oops something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Oops something went wrong!!", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteAll:
                viewModel.deleteAll();
                Toast.makeText(this, "All Elements Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }
}
