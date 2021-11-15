package com.example.dominictools.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dominictools.R
import com.example.dominictools.data.entities.Loan

class BorrowedAdapter(private val listener: BorrowedClickListener) : ListAdapter<Loan, BorrowedAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Loan>() {
            override fun areItemsTheSame(oldData: Loan, newData: Loan): Boolean {
                return oldData.toolName == newData.toolName && oldData.friendName == newData.friendName
            }

            override fun areContentsTheSame(oldData: Loan, newData: Loan): Boolean {
                return oldData == newData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorrowedAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_borrowed, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: BorrowedAdapter.MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var toolName: TextView = itemView.findViewById(R.id.tvToolName)
        var toolBorrowed: TextView = itemView.findViewById(R.id.tvToolBorrowed)

        fun bind(loan: Loan){
            toolName.text = loan.toolName
            val borrowedText = "${loan.count} Borrowed"
            toolBorrowed.text = borrowedText
            itemView.setOnClickListener { listener.onBorrowedClicked(adapterPosition, loan) }
        }
    }

    interface BorrowedClickListener{
        fun onBorrowedClicked(position: Int, loan: Loan)
    }
}