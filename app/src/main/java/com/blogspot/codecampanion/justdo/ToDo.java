package com.blogspot.codecampanion.justdo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class ToDo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String task;

    private String subtask;

    public ToDo(String task, String subtask) {
        this.task = task;
        this.subtask = subtask;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getSubtask() {
        return subtask;
    }
}
