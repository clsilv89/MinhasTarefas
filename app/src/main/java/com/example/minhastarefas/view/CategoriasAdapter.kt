package com.example.minhastarefas.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minhastarefas.databinding.ItemLayoutCategoriaBinding
import com.example.minhastarefas.model.Tarefa
import com.example.minhastarefas.databinding.ItemLayoutTarefaBinding
import com.example.minhastarefas.databinding.ItemLayoutTarefaCompletaBinding
import com.example.minhastarefas.model.Categoria

class CategoriasAdapter :
    ListAdapter<Categoria, CategoriasAdapter.CategoriasViewHolder>(DiffCallback()) {

    var onClick: (Categoria) -> Unit = {}

    inner class CategoriasViewHolder(val binding: ItemLayoutCategoriaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoria: Categoria) {
            binding.textViewNomeCategoria.text = categoria.name
            binding.root.setOnClickListener {
                onClick(categoria)
                notifyDataSetChanged()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Categoria>() {
        override fun areItemsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriasViewHolder {
        return CategoriasViewHolder(
            ItemLayoutCategoriaBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriasViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private const val COMPLETA = 1
        private const val NAO_COMPLETA = 0
    }
}