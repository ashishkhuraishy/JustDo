package com.blogspot.codecampanion.justdo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private List<ToDo> tasks = new ArrayList<>();
    private SetOnItemClickListener listener;

    public void setTasks(List<ToDo> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public ToDo getToDoAt(int position) {
        return tasks.get(position);
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {

        private TextView taskText;
        private TextView subTaskText;

        ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            subTaskText = itemView.findViewById(R.id.textView2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(tasks.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_activity, parent, false
        );

        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        ToDo currentItem = tasks.get(position);

        holder.taskText.setText(currentItem.getTask());
        holder.subTaskText.setText(currentItem.getSubtask());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setListener(SetOnItemClickListener listener) {
        this.listener = listener;
    }

    public interface SetOnItemClickListener {
        void OnItemClick(ToDo toDo);
    }

}
