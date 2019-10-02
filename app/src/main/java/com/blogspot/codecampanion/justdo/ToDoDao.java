package com.blogspot.codecampanion.justdo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoDao {

    @Insert
    void insert(ToDo toDo);

    @Update
    void update(ToDo toDo);

    @Delete
    void delete(ToDo toDo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("SELECT * FROM todo_table")
    LiveData<List<ToDo>> getAllTasks();

}
