package com.example.studentdetailsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STUDENT = "com.example.studentdetailsapp.EXTRA_STUDENT"
        const val EXTRA_STUDENTS = "com.example.studentdetailsapp.EXTRA_STUDENTS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val students = ArrayList<Student>()

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val rollInput = findViewById<EditText>(R.id.rollInput)
        val marksInput = findViewById<EditText>(R.id.marksInput)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val departmentInput = findViewById<EditText>(R.id.departmentInput)

        val addButton = findViewById<Button>(R.id.addButton)
        fun saveStudent(){
            val name = nameInput.text.toString()
            val roll = rollInput.text.toString().toIntOrNull() ?: 0
            val marks = marksInput.text.toString().toIntOrNull() ?: 0
            val department = departmentInput.text.toString()

            // Package all three fields into ONE Parcelable object,
            // instead of three separate putExtra() calls.
            val student = Student(name, roll, marks , department)
            students.add(student)
            Toast.makeText(this, "Student $name Added" , Toast.LENGTH_SHORT).show()
            nameInput.text.clear()
            rollInput.text.clear()
            marksInput.text.clear()
            departmentInput.text.clear()
        }

        addButton.setOnClickListener {
            saveStudent()
        }


        submitButton.setOnClickListener {
//            val name = nameInput.text.toString()
//            val roll = rollInput.text.toString()
//            val marks = marksInput.text.toString().toIntOrNull() ?: 0
//            val department = departmentInput.text.toString()
//
//            // Package all three fields into ONE Parcelable object,
//            // instead of three separate putExtra() calls.
//            val student = Student(name, roll, marks , department)
                saveStudent()

//
            val intent = Intent(this, SecondActivity::class.java)
//            intent.putExtra(EXTRA_STUDENT, student)
            intent.putParcelableArrayListExtra(EXTRA_STUDENTS , students)

            startActivity(intent)
        }


    }
}
