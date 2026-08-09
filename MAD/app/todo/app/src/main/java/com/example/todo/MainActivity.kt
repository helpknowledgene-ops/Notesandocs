package com.example.todo

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input=findViewById<EditText>(R.id.etTask)
        val btn=findViewById<Button>(R.id.btnAdd)
        val rv=findViewById<RecyclerView>(R.id.recyclerView)

        val adapter=TodoAdapter(mutableListOf())
        rv.layoutManager=LinearLayoutManager(this)
        rv.adapter=adapter

        btn.setOnClickListener{
            val text=input.text.toString().trim()
            if(text.isNotEmpty()){
                adapter.addTodo(Todo(text))
                input.text.clear()
            }
        }
    }
}