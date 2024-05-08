package com.example.minhastarefas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minhastarefas.R
import com.example.minhastarefas.databinding.FragmentListaTarefasBinding

class ListaTarefasFragment : Fragment() {

    private lateinit var binding: FragmentListaTarefasBinding
    private lateinit var adapter: TarefasAdapter
    private lateinit var categoriasAdapter: CategoriasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaTarefasBinding.inflate(inflater)
        adapter = (activity as MainActivity).adapter
        categoriasAdapter = (activity as MainActivity).categoriasAdapter

        binding.botaoCriaTarefa.setOnClickListener {
            findNavController().navigate(R.id.action_listaTarefasFragment_to_criaTarefaFragment)
        }

        configuraRecyclewView()

        return binding.root
    }

    private fun configuraRecyclewView() {
        binding.recyclewViewTarefas.adapter = adapter
        binding.recyclewViewTarefas.layoutManager = LinearLayoutManager(activity)
        binding.recyclewViewCategorias.adapter = categoriasAdapter
        binding.recyclewViewCategorias.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    companion object {
        private var criarNovaTarefa: () -> Unit = {}

        @JvmStatic
        fun newInstance(criaTarefa: () -> Unit = {}, param2: String): ListaTarefasFragment {
            criarNovaTarefa = criaTarefa
            return ListaTarefasFragment().apply {
                arguments = Bundle().apply {

                }
            }
        }
    }
}