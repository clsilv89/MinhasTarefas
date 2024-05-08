package com.example.minhastarefas.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoriasDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(categoria: Categoria)

    @Query("SELECT * FROM categorias")
    suspend fun getAllCategories(): List<Categoria>
}