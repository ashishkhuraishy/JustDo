package com.blogspot.codecampanion.justdo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ToDo.class, version = 1)
public abstract class ToDoDataBase extends RoomDatabase {

    private static ToDoDataBase instance;

    public abstract ToDoDao toDoDao();

    public static synchronized ToDoDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ToDoDataBase.class, "todo_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
