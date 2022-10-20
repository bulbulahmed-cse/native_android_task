package com.example.nativeandroidtask.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nativeandroidtask.databinding.ItemTodosBinding
import com.example.nativeandroidtask.databinding.UserItemBinding
import com.example.nativeandroidtask.models.Todos
import com.example.nativeandroidtask.models.User
import com.example.nativeandroidtask.views.fragments.todos.TodosFragment

class TodosAdapter(private var list: List<Todos>,) :RecyclerView.Adapter<TodosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodosAdapter.ViewHolder {
        return ViewHolder(
            ItemTodosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodosAdapter.ViewHolder, position: Int) {
        holder.binding.tvTodosTitle.text = list[position].title
        holder.binding.tvUserId.text = "User Id: "+list[position].userId.toString()
        holder.binding.tvId.text = "Id: "+list[position].userId.toString()
        holder.binding.cbStatus.isChecked = list[position].completed!!
    }

    override fun getItemCount() = list.size

    fun setItem(list:List<Todos>){
        this.list = list
    }


    inner class ViewHolder(val binding: ItemTodosBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}