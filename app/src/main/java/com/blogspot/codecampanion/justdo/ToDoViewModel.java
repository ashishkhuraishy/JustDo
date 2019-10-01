package com.blogspot.codecampanion.justdo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {


    private ToDoRepository repository;
    private LiveData<List<ToDo>> data;

    public ToDoViewModel(@NonNull Application application) {
        super(application);

        repository = new ToDoRepository(application);
        data = repository.getToDoList();
    }

    public void insert(ToDo toDo){
        repository.insert(toDo);
    }

    public void update(ToDo toDo){
        repository.update(toDo);
    }

    public void delete(ToDo toDo){
        repository.delete(toDo);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<ToDo>> getData() {
        return data;
    }
}
