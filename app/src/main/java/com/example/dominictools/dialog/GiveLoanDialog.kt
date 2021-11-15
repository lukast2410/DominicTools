package com.example.dominictools.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dominictools.R
import com.example.dominictools.data.ToolsDb
import com.example.dominictools.data.entities.Loan
import com.example.dominictools.data.entities.relations.ToolAndLoans
import com.example.dominictools.factory.MainViewModelFactory
import com.example.dominictools.viewmodel.MainViewModel

class GiveLoanDialog(private val toolAndLoans: ToolAndLoans) : DialogFragment() {

    private val viewModel: MainViewModel by lazy {
        val dataSource = ToolsDb.getInstance(requireContext()).dao
        val factory = MainViewModelFactory(dataSource)
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_give_loan, null, false)
        val spinner: Spinner = view.findViewById(R.id.spinner_friends)
        settingSpinner(spinner)

        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle(R.string.select_friend)
            setView(view)
            setPositiveButton(R.string.ok) {_, _ ->
                saveLoan(spinner.selectedItem.toString())
            }
            setNegativeButton(R.string.cancel){_, _ -> dismiss()}
        }
        return builder.create()
    }

    private fun saveLoan(name: String) {
        val borrowed = toolAndLoans.loans.sumOf { it.count }
        if(toolAndLoans.tool.count - borrowed <= 0){
            Toast.makeText(context, "${toolAndLoans.tool.name} ${getString(R.string.not_available)}", Toast.LENGTH_LONG).show()
            return
        }

        val res = viewModel.getLoan(name, toolAndLoans.tool.name)
        if(res == null){
            viewModel.insertLoan(Loan(friendName = name, toolName = toolAndLoans.tool.name, count = 1))
        }
        else{
            res.count++
            viewModel.updateLoan(res)
        }
        Toast.makeText(context, "$name ${getString(R.string.loan_saved)}", Toast.LENGTH_SHORT).show()
    }

    private fun settingSpinner(spinner: Spinner) {
        val friendNames: ArrayList<String> = ArrayList()
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, friendNames)

        viewModel.getFriends().observe(this, Observer {
            friendNames.clear()
            it.forEach { x ->
                friendNames.add(x.name)
            }
            spinner.adapter = adapter
        })
    }
}