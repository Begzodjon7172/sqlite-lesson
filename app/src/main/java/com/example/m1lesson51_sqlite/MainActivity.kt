package com.example.m1lesson51_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.m1lesson51_sqlite.adapters.StudentAdapter
import com.example.m1lesson51_sqlite.database.MyDbHelper
import com.example.m1lesson51_sqlite.databinding.ActivityMainBinding
import com.example.m1lesson51_sqlite.databinding.ItemDialogBinding
import com.example.m1lesson51_sqlite.models.Student

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var myDbHelper: MyDbHelper
    private lateinit var studentList: ArrayList<Student>
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)
        studentList = ArrayList(myDbHelper.listStudents())

        binding.apply {

            saveBtn.setOnClickListener {
                val name = nameEt.text.toString()
                val age = nameEt.text.toString().toInt()
                val phone = nameEt.text.toString()

                val student = Student(name = name, age = age, phoneNumber = phone)
                myDbHelper.addStudent(student)
                studentList.add(student)
                studentAdapter.notifyItemInserted(studentList.size)
            }

            studentAdapter = StudentAdapter(studentList, { student, position ->
                myDbHelper.deleteStudent(student)
                studentList.remove(student)
                studentAdapter.notifyItemRemoved(position)
                studentAdapter.notifyItemRangeChanged(position, studentList.size)
            }, { student, position ->
                showEditDialog(student, position)
            }, { student ->
                val intent = Intent(this@MainActivity, StudentActivity::class.java)
                intent.putExtra("id", student.id)
                startActivity(intent)
            })

            rv.adapter = studentAdapter

        }

    }

    private fun showEditDialog(student: Student, position: Int) {
        val alertDialog = AlertDialog.Builder(this)
        val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
        alertDialog.setView(itemDialogBinding.root)
        val alert = alertDialog.create()

        itemDialogBinding.apply {
            edtName.setText(student.name)
            edtAge.setText(student.age.toString())
            edtPhone.setText(student.phoneNumber)

            editBtn.setOnClickListener {
                val newName = edtName.text.toString()
                val newAge = edtName.text.toString().toInt()
                val newPhone = edtPhone.text.toString()
                student.name = newName
                student.age = newAge
                student.phoneNumber = newPhone
                myDbHelper.editStudent(student)
                studentAdapter.notifyItemChanged(position)
                alert.dismiss()
            }
        }

    }
}