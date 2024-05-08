package com.example.minhastarefas.model

class CategoriasRepository(private val dao: CategoriasDao) {
    suspend fun getCategorias(): List<Categoria> {
        return dao.getAllCategories()
    }

    suspend fun insertCategoria(categoria: Categoria) {
        dao.insert(categoria)
    }
}