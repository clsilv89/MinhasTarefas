package com.example.minhastarefas.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TarefasDao {
    @Insert
    suspend fun insertTarefa(tarefa: Tarefa)

    @Delete
    suspend fun deleteTarefa(tarefa: Tarefa): Int

    @Update()
    suspend fun updateTarefa(tarefa: Tarefa)

    @Query("delete from tarefas")
    suspend fun deleteAllTarefas()

    @Query("select * from tarefas")
    suspend fun getAllTarefas(): List<Tarefa>

    @Query("select * from tarefas where completa == true")
    suspend fun getTarefasCompletas(): List<Tarefa>
}