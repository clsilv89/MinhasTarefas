package com.example.minhastarefas.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tarefa::class], version = 1, exportSchema = false)
abstract class TarefasDatabase : RoomDatabase() {
    abstract fun tarefasDao(): TarefasDao

    companion object {
        private var INSTANCE: TarefasDatabase? = null

        fun getDatabase(context: Context): TarefasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarefasDatabase::class.java,
                    "tarefas-database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}