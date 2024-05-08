package com.example.minhastarefas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "descricao")
    val descricao: String,
    @ColumnInfo(name = "completa")
    var completa: Boolean,
)
