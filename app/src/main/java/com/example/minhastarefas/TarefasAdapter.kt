package com.example.minhastarefas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minhastarefas.databinding.ItemLayoutTarefaBinding
import com.example.minhastarefas.databinding.ItemLayoutTarefaCompletaBinding

class TarefasAdapter : ListAdapter<Tarefa, RecyclerView.ViewHolder>(DiffCallback()) {

    var onClick: (Tarefa) -> Unit = {}

    inner class TarefasViewHolder(val binding: ItemLayoutTarefaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tarefa: Tarefa) {
            binding.textViewDescricaoTarefa.text = tarefa.descricao
            binding.root.setOnClickListener {
                onClick(tarefa)
                notifyDataSetChanged()
            }
        }
    }

    inner class TarefasCompletasViewHolder(val binding: ItemLayoutTarefaCompletaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tarefa: Tarefa) {
            binding.textViewDescricaoTarefa.text = tarefa.descricao
            binding.root.setOnLongClickListener {
                onClick(tarefa)
                notifyDataSetChanged()
                return@setOnLongClickListener true
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Tarefa>() {
        override fun areItemsTheSame(oldItem: Tarefa, newItem: Tarefa): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Tarefa, newItem: Tarefa): Boolean {
            return oldItem.descricao == newItem.descricao
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).completa) COMPLETA else NAO_COMPLETA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val tarefasViewHolder = TarefasViewHolder(
            ItemLayoutTarefaBinding.inflate(layoutInflater, parent, false)
        )
        val tarefaCompletaViewHolder = TarefasCompletasViewHolder(
            ItemLayoutTarefaCompletaBinding.inflate(layoutInflater, parent, false)
        )

        return when (viewType) {
            COMPLETA -> tarefaCompletaViewHolder
            NAO_COMPLETA -> tarefasViewHolder
            else -> tarefasViewHolder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TarefasViewHolder -> {
                holder.bind(getItem(position))
            }

            is TarefasCompletasViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    companion object {
        private const val COMPLETA = 1
        private const val NAO_COMPLETA = 0
    }
}