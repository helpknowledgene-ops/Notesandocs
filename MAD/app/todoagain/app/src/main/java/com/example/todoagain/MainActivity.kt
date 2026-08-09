package com.example.todoagain

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import com.example.todoagain.model.Todo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoText = findViewById<EditText>(R.id.textInput)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val todos = ArrayList<Todo>()

        val todosList : RecyclerView


        buttonAdd.setOnClickListener {
            val title = todoText.toString()

            val todo = Todo(title)
            todos.add(todo)
        }

        }
    }
