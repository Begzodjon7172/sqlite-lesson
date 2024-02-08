package com.example.m1lesson51_sqlite.database

import com.example.m1lesson51_sqlite.models.Student

interface DatabaseService {

    fun addStudent(student: Student)

    fun listStudents(): List<Student>

    fun editStudent(student: Student)

    fun deleteStudent(student: Student)

    fun getStudentsCount(): Int

    fun getStudentById(id: Int): Student

}
