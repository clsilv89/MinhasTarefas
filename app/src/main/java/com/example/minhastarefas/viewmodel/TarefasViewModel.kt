package com.example.minhastarefas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.minhastarefas.model.Categoria
import com.example.minhastarefas.model.CategoriasDao
import com.example.minhastarefas.model.CategoriasDatabase
import com.example.minhastarefas.model.CategoriasRepository
import com.example.minhastarefas.model.Tarefa
import com.example.minhastarefas.model.TarefasDao
import com.example.minhastarefas.model.TarefasDatabase
import com.example.minhastarefas.model.TarefasRepository
import kotlinx.coroutines.launch

class TarefasViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TarefasRepository
    private var categoriasRepository: CategoriasRepository

    private var _tarefas: MutableLiveData<List<Tarefa>> = MutableLiveData<List<Tarefa>>()
    val tarefas: LiveData<List<Tarefa>>
        get() = _tarefas

    private var _categorias: MutableLiveData<List<Categoria>> = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>>
        get() = _categorias

    init {
        var tarefasDao: TarefasDao = TarefasDatabase.getDatabase(application).tarefasDao()
        var categoriasDao: CategoriasDao =
            CategoriasDatabase.getDatabase(application).categoriaDao()
        repository = TarefasRepository(tarefasDao)
        categoriasRepository = CategoriasRepository(categoriasDao)
        getAllTarefas()
    }

    fun getAllCategorias() {
        viewModelScope.launch {
            _categorias.postValue(categoriasRepository.getCategorias())
        }
    }

    fun getAllTarefas() {
        viewModelScope.launch {
            _tarefas.postValue(repository.getAllTarefas())
        }
    }

    fun addTarefa(tarefa: Tarefa) {
        viewModelScope.launch {
            repository.insertTarefa(tarefa).also {
                getAllTarefas()
            }
        }
    }

    fun addCategoria(categoria: Categoria) {
        viewModelScope.launch {
            categoriasRepository.insertCategoria(categoria).also {
                getAllCategorias()
            }
        }
    }

    fun updateTarefa(tarefa: Tarefa) {
        viewModelScope.launch {
            repository.updateTarefa(
                Tarefa(
                    tarefa.id,
                    tarefa.descricao,
                    !tarefa.completa
                )
            )
        }
        getAllTarefas()
    }
}