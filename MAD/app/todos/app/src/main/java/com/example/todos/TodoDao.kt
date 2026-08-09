package com.example.todos

import androidx.room.*

@Dao
interface TodoDao {

    @Insert
    suspend fun  insert(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Query("select * from todo_table")
    suspend fun getAllTodos(): List<Todo>
}