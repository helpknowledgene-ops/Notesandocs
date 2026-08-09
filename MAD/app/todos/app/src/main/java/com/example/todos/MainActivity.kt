package com.example.todos

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input=findViewById<EditText>(R.id.etTask)
        val btn=findViewById<Button>(R.id.btnAdd)
        val rv=findViewById<RecyclerView>(R.id.recyclerView)

//        Room
        val db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_database"
        ).build()

        val dao = db.todoDao()

        val adapter = TodoAdapter(
            mutableListOf(),
            onCheckChanged = { todo -> lifecycleScope.launch { dao.update(todo) } },
            onDelete = { todo -> lifecycleScope.launch { dao.delete(todo) } }
        )
        rv.layoutManager=LinearLayoutManager(this)
        rv.adapter=adapter


        lifecycleScope.launch {
            val todos = dao.getAllTodos()
            adapter.setTodos(todos)
        }

        btn.setOnClickListener{
            val text=input.text.toString().trim()
            if(text.isNotEmpty()){
//                adapter.addTodo(Todo(text))
                lifecycleScope.launch {
                    val todo = Todo(title = text)
                    dao.insert(todo)
                    adapter.setTodos(dao.getAllTodos())


                }
                input.text.clear()

            }
        }
    }
}