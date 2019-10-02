package com.blogspot.codecampanion.justdo;

import android.app.Application;
        import android.os.AsyncTask;

        import androidx.lifecycle.LiveData;

        import java.util.List;

public class ToDoRepository {

    private ToDoDao toDoDao;
    private LiveData<List<ToDo>> toDoList;

    public ToDoRepository(Application application){
        ToDoDataBase dataBase = ToDoDataBase.getInstance(application);
        toDoDao = dataBase.toDoDao();
        toDoList = toDoDao.getAllTasks();

    }

    public void insert(ToDo toDo){
        new InsertAsynctask(toDoDao).execute(toDo);

    }

    public void update(ToDo toDo){
        new UpdateAsynctask(toDoDao).execute(toDo);

    }

    public void delete(ToDo toDo){
        new DeleteAsynctask(toDoDao).execute(toDo);
    }

    public void deleteAll(){
        new DeleteAllAsynctask(toDoDao).execute();
    }

    public LiveData<List<ToDo>> getToDoList() {
        return toDoList;
    }



    private static class InsertAsynctask extends AsyncTask<ToDo, Void, Void>{

        private ToDoDao toDoDao;

        private InsertAsynctask(ToDoDao toDoDao){
            this.toDoDao = toDoDao;
        }

        @Override
        protected Void doInBackground(ToDo... toDos) {
            toDoDao.insert(toDos[0]);
            return null;
        }
    }

    private static class UpdateAsynctask extends AsyncTask<ToDo, Void, Void>{

        private ToDoDao toDoDao;

        private UpdateAsynctask(ToDoDao toDoDao){
            this.toDoDao = toDoDao;
        }

        @Override
        protected Void doInBackground(ToDo... toDos) {
            toDoDao.update(toDos[0]);
            return null;
        }
    }

    private static class DeleteAsynctask extends AsyncTask<ToDo, Void, Void>{

        private ToDoDao toDoDao;

        private DeleteAsynctask(ToDoDao toDoDao){
            this.toDoDao = toDoDao;
        }

        @Override
        protected Void doInBackground(ToDo... toDos) {
            toDoDao.delete(toDos[0]);
            return null;
        }
    }

    private static class DeleteAllAsynctask extends AsyncTask<Void, Void, Void>{

        private ToDoDao toDoDao;

        private DeleteAllAsynctask(ToDoDao toDoDao){
            this.toDoDao = toDoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            toDoDao.deleteAll();
            return null;
        }
    }



}


