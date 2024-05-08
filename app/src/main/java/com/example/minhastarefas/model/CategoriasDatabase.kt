package com.example.minhastarefas.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Categoria::class], version = 1, exportSchema = false)
abstract class CategoriasDatabase : RoomDatabase() {
    abstract fun categoriaDao(): CategoriasDao

    companion object {
        private var INSTANCE: CategoriasDatabase? = null

        fun getDatabase(context: Context): CategoriasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CategoriasDatabase::class.java,
                    "categorias_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}