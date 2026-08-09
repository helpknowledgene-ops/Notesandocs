package com.example.todoagain

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoagain.model.Todo

class TodoAdapter(
    private val todoList: ArrayList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int
    ) {
//        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")

        return todoList.size
    }

    class TodoViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView)
}