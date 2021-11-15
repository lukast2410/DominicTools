package com.example.dominictools

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.dominictools.adapter.BorrowedAdapter
import com.example.dominictools.data.ToolsDb
import com.example.dominictools.data.entities.Loan
import com.example.dominictools.factory.MainViewModelFactory
import com.example.dominictools.viewmodel.MainViewModel

class FriendDetailActivity : AppCompatActivity(), BorrowedAdapter.BorrowedClickListener {
    private lateinit var name: String
    private lateinit var tvFriendName: TextView
    private lateinit var tvEmpty: TextView
    private lateinit var llContainer: LinearLayout
    private lateinit var rvBorrowedTools: RecyclerView
    private lateinit var adapter: BorrowedAdapter

    private val viewModel: MainViewModel by lazy {
        val dataSource = ToolsDb.getInstance(this).dao
        val factory = MainViewModelFactory(dataSource)
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_detail)
        getIntentData()
        initializeActionBar()
        initializeComponent()
        initializeBorrowedTools()
        setting()
    }

    private fun setting() {
        tvFriendName.text = name
        viewModel.getFriendAndLoan(name).observe(this, Observer {
            if(it.loans.isEmpty()){
                tvEmpty.visibility = View.VISIBLE
                llContainer.visibility = View.GONE
            }else{
                tvEmpty.visibility = View.GONE
                llContainer.visibility = View.VISIBLE
            }
            adapter.submitList(it.loans)
        })
    }

    private fun initializeBorrowedTools() {
        rvBorrowedTools = findViewById(R.id.rvBorrowedTools)
        adapter = BorrowedAdapter(this)
        rvBorrowedTools.adapter = adapter
    }

    private fun initializeComponent() {
        tvFriendName = findViewById(R.id.tvFriendName)
        tvEmpty = findViewById(R.id.tvBorrowedEmpty)
        llContainer = findViewById(R.id.llBorrowedTools)
    }

    private fun initializeActionBar() {
        supportActionBar?.setTitle(R.string.friend_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getIntentData() {
        name = intent.getStringExtra("FriendName").toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBorrowedClicked(position: Int, loan: Loan) {
        val builder = AlertDialog.Builder(this)
            .setMessage(R.string.delete_confirmation)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteLoan(loan)
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
        builder.show()
    }
}