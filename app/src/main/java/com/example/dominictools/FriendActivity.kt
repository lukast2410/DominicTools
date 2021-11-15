package com.example.dominictools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.dominictools.adapter.FriendAdapter
import com.example.dominictools.adapter.ToolAndLoanAdapter
import com.example.dominictools.data.ToolsDb
import com.example.dominictools.data.entities.Friend
import com.example.dominictools.data.entities.relations.ToolAndLoans
import com.example.dominictools.dialog.GiveLoanDialog
import com.example.dominictools.factory.MainViewModelFactory
import com.example.dominictools.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView

class FriendActivity : AppCompatActivity(), FriendAdapter.FriendClickListener {
    private lateinit var adapter: FriendAdapter
    private lateinit var rvFriends: RecyclerView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private val viewModel: MainViewModel by lazy {
        val dataSource = ToolsDb.getInstance(this).dao
        val factory = MainViewModelFactory(dataSource)
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend)
        initializeDrawer()
        initialize()
        setting()
    }

    private fun initializeDrawer() {
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_open, R.string.navigation_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navHome -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    private fun setting() {
        viewModel.getFriends().observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initialize() {
        adapter = FriendAdapter(this)
        rvFriends = findViewById(R.id.rvFriends)
        rvFriends.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(toggle.onOptionsItemSelected(item)){
            true
        }else super.onOptionsItemSelected(item)
    }

    override fun onFriendClicked(position: Int, friend: Friend) {
        val intent = Intent(this, FriendDetailActivity::class.java)
        intent.putExtra("FriendName", friend.name)
        startActivity(intent)
    }
}