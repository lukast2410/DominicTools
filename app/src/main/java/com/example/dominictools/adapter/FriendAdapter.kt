package com.example.dominictools.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dominictools.R
import com.example.dominictools.data.entities.Friend

class FriendAdapter(private val listener: FriendClickListener) : ListAdapter<Friend, FriendAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Friend>() {
            override fun areItemsTheSame(oldData: Friend, newData: Friend): Boolean {
                return oldData.name == newData.name
            }

            override fun areContentsTheSame(oldData: Friend, newData: Friend): Boolean {
                return oldData == newData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_friend, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendAdapter.MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFriend: TextView = itemView.findViewById(R.id.tvFriendName)

        fun bind(friend: Friend){
            tvFriend.text = friend.name
            itemView.setOnClickListener { listener.onFriendClicked(adapterPosition, friend) }
        }
    }

    interface FriendClickListener{
        fun onFriendClicked(position: Int, friend: Friend)
    }
}