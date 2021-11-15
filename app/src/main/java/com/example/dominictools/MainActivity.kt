package com.example.dominictools

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.dominictools.adapter.ToolAndLoanAdapter
import com.example.dominictools.data.ToolsDb
import com.example.dominictools.data.entities.relations.ToolAndLoans
import com.example.dominictools.dialog.GiveLoanDialog
import com.example.dominictools.factory.MainViewModelFactory
import com.example.dominictools.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), ToolAndLoanAdapter.ToolClickListener {
    private lateinit var adapter: ToolAndLoanAdapter
    private lateinit var rvTools: RecyclerView
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
        setContentView(R.layout.activity_main)
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
                R.id.navFriends -> {
                    val intent = Intent(this, FriendActivity::class.java)
                    startActivity(intent)
                    drawerLayout.close()
                }
            }
            true
        }
    }

    private fun setting() {
        viewModel.getToolAndLoans().observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initialize() {
        adapter = ToolAndLoanAdapter(this)
        rvTools = findViewById(R.id.rvTools)
        rvTools.adapter = adapter
    }

    override fun onToolClicked(position: Int, toolAndLoans: ToolAndLoans) {
        GiveLoanDialog(toolAndLoans).show(supportFragmentManager, "GiveLoan")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(toggle.onOptionsItemSelected(item)){
            true
        }else super.onOptionsItemSelected(item)
    }
}