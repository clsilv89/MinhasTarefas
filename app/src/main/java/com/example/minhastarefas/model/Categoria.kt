package com.example.minhastarefas.model

import androidx.room.Entity

@Entity(tableName = "categorias", primaryKeys = ["name"])
data class Categoria(
    val name: String
)
