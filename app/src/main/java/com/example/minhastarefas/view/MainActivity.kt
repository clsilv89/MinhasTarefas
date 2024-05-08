package com.example.minhastarefas.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.minhastarefas.model.Tarefa
import com.example.minhastarefas.viewmodel.TarefasViewModel
import com.example.minhastarefas.databinding.ActivityMainBinding
import com.example.minhastarefas.model.Categoria
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    private val gson = GsonBuilder().create()
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TarefasViewModel
    private var navController: NavController? = null
    private val listaCategorias = arrayListOf<Categoria>()

    val adapter = TarefasAdapter()
    val categoriasAdapter = CategoriasAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        sharedPrefs = this.getPreferences(Context.MODE_PRIVATE)
        viewModel = TarefasViewModel(application = application)

        setContentView(binding.root)

        binding.clearFilterFab.setOnClickListener {
            binding.clearFilterFab.isVisible = false
            adapter.submitList(viewModel.tarefas.value)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id)
        navController = navHostFragment?.findNavController()

        navController?.let { navController ->
            setupActionBarWithNavController(navController)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController?.navigateUp() == true) {
                    navController?.navigateUp()
                } else {
                    this@MainActivity.finish()
                }
            }
        })

        adapter.onClick = {
            updateTarefa(it)
        }
        categoriasAdapter.onClick = { categoria ->
            binding.clearFilterFab.isVisible = true
            adapter.submitList(
                adapter.currentList.filter { tarefa ->
                    tarefa.descricao.contains(categoria.name)
                }
            )
        }
        setupObservers()
    }

//    override fun onBackPressed() {
//        navController?.navigateUp()
//    }

    private fun setupObservers() {
        viewModel.tarefas.observe(this) {
            adapter.submitList(it)
        }
        viewModel.categorias.observe(this) {
            listaCategorias.addAll(it)
            categoriasAdapter.submitList(listaCategorias)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false || super.onSupportNavigateUp()
    }

    fun getTarefa(descricaoTarefa: String) {
        val tarefa = Tarefa(
            descricao = descricaoTarefa,
            completa = false
        )
        categorizaTarefa(descricaoTarefa)
        salvarTarefa(tarefa)
        navController?.navigateUp()
    }


    private fun salvarTarefa(tarefa: Tarefa) {
        viewModel.addTarefa(tarefa)
    }

    private fun updateTarefa(tarefa: Tarefa) {
        viewModel.updateTarefa(tarefa)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllTarefas()
        viewModel.getAllCategorias()
    }

    private fun categorizaTarefa(tarefa: String) {
        val wordArray = tarefa.trim().split("\\s+".toRegex())
        for (i in wordArray) {
            if (i.contains("#")) {
                if (viewModel.categorias.value?.contains(Categoria(i)) != false) {
                    viewModel.addCategoria(Categoria(i))
                }
            }
        }
    }
}