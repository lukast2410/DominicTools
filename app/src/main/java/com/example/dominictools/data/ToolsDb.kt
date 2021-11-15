package com.example.dominictools.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dominictools.data.entities.Friend
import com.example.dominictools.data.entities.Loan
import com.example.dominictools.data.entities.Tool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(entities = [Friend::class, Tool::class, Loan::class], version = 1, exportSchema = false)
abstract class ToolsDb : RoomDatabase() {
    abstract val dao: ToolsDao

    companion object {
        @Volatile
        private var INSTANCE: ToolsDb? = null

        fun getInstance(context: Context): ToolsDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ToolsDb::class.java,
                        "tools.db"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadExecutor().execute {
                                instance?.let{
                                    CoroutineScope(Dispatchers.IO).launch {
                                        it.dao.insertFriends(DataGenerator.getInitialFriends())
                                        it.dao.insertTools(DataGenerator.getInitialTools())
                                    }
                                }
                            }
                        }
                    }).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}