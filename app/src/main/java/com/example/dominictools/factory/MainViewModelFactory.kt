/*
 * Copyright (c) 2020 Indra Azimi. All rights reserved.
 *
 *  Dibuat untuk kelas Pemrograman untuk Perangkat Bergerak 2.
 *  Dilarang melakukan penggandaan dan atau komersialisasi,
 *  sebagian atau seluruh bagian, baik cetak maupun elektronik
 *  terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.example.dominictools.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dominictools.data.ToolsDao
import com.example.dominictools.viewmodel.MainViewModel

class MainViewModelFactory(private val dataSource: ToolsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}
