package com.example.m1lesson51_sqlite.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.m1lesson51_sqlite.databinding.ItemStudentBinding
import com.example.m1lesson51_sqlite.models.Student

class StudentAdapter(
    private val studentList: List<Student>,
    private val onItemDelete: (Student, Int) -> Unit,
    private val onItemEdit: (Student, Int) -> Unit,
    private val onItemClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.Vh>() {

    inner class Vh(private val itemStudentBinding: ItemStudentBinding) :
        RecyclerView.ViewHolder(itemStudentBinding.root) {
        fun onBind(student: Student, position: Int) {
            itemStudentBinding.apply {
                tvName.text = student.name

                deleteBtn.setOnClickListener {
                    onItemDelete.invoke(student, position)
                }
                editBtn.setOnClickListener {
                    onItemEdit.invoke(student, position)
                }
                itemView.setOnClickListener{
                    onItemClick.invoke(student)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = studentList.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(studentList[position], position)
    }
}