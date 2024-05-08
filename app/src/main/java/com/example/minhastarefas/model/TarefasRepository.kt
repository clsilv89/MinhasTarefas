package com.example.minhastarefas.model

class TarefasRepository(private val dao: TarefasDao) {
    suspend fun insertTarefa(tarefa: Tarefa) {
        dao.insertTarefa(tarefa)
    }

    suspend fun deleteTarefa(tarefa: Tarefa): Int {
        return dao.deleteTarefa(tarefa)
    }

    suspend fun updateTarefa(tarefa: Tarefa) {
        dao.updateTarefa(tarefa)
    }

    suspend fun deleteAllTarefas() {
        dao.deleteAllTarefas()
    }

    suspend fun getAllTarefas(): List<Tarefa> {
        return dao.getAllTarefas()
    }

    suspend fun getTarefasCompletas(): List<Tarefa> {
        return dao.getTarefasCompletas()
    }
}