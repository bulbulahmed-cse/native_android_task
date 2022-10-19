package com.example.nativeandroidtask.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nativeandroidtask.databinding.UserItemBinding
import com.example.nativeandroidtask.models.User
import com.example.nativeandroidtask.views.fragments.todos.TodosFragment

class UserAdapter(private val list: List<User>,) :RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAdapter.ViewHolder {
        return ViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.binding.userNameTextView.text = list[position].name
    }

    override fun getItemCount() = list.size


    inner class ViewHolder(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}