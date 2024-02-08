package com.example.m1lesson51_sqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.m1lesson51_sqlite.models.Student

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    DatabaseService {

    companion object {
        const val DB_NAME = "m1lesson51"
        const val DB_VERSION = 1

        const val TABLE_NAME = "student"
        const val STUDENT_ID = "id"
        const val STUDENT_NAME = "name"
        const val STUDENT_AGE = "age"
        const val PHONE_NUMBER = "phone_number"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        // database yaratilgandan so'ng ishga tushadi

        val query =
            "create table $TABLE_NAME ($STUDENT_ID integer not null primary key autoincrement, $STUDENT_NAME text not null, $STUDENT_AGE integer not null, $PHONE_NUMBER text not null unique)"
        p0?.execSQL(query)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addStudent(student: Student) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME, student.name)
        contentValues.put(STUDENT_AGE, student.age)
        contentValues.put(PHONE_NUMBER, student.phoneNumber)
        database.insert(TABLE_NAME, null, contentValues)
    }

    override fun listStudents(): List<Student> {
        val list = ArrayList<Student>()

        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val age = cursor.getInt(2)
                val phoneNumber = cursor.getString(3)

                val student = Student(id, name, age, phoneNumber)
                list.add(student)

            } while (cursor.moveToNext())
        }
        return list
    }

    override fun editStudent(student: Student) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME, student.name)
        contentValues.put(STUDENT_AGE, student.age)
        contentValues.put(PHONE_NUMBER, student.phoneNumber)
        database.update(
            TABLE_NAME,
            contentValues,
            "$STUDENT_ID = ?",
            arrayOf(student.id.toString())
        )
    }

    override fun deleteStudent(student: Student) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$STUDENT_ID = ?", arrayOf(student.id.toString()))
    }

    override fun getStudentsCount(): Int {
        val database = this.readableDatabase
        val cursor = database.rawQuery("select * from $TABLE_NAME", null)
        return cursor.count
    }

    override fun getStudentById(id: Int): Student {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_NAME,
            arrayOf(STUDENT_ID, STUDENT_NAME, STUDENT_AGE, PHONE_NUMBER),
            "$STUDENT_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor.moveToFirst()
        return Student(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3))
    }
}