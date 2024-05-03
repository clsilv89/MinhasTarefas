package com.example.minhastarefas

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.minhastarefas.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity() {

    private val gson = GsonBuilder().create()
    private lateinit var sharedPrefs: SharedPreferences

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private val listaTarefas = arrayListOf<Tarefa>()
    val adapter = TarefasAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        sharedPrefs = this.getPreferences(Context.MODE_PRIVATE)

        setContentView(binding.root)

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

        recuperaLista("TAREFAS")

        adapter.submitList(listaTarefas)
        adapter.onClick = {
            val index = listaTarefas.indexOf(it)

            listaTarefas[index].apply {
                this.completa = !this.completa
            }
            salvarLista(listaTarefas)
        }
    }

//    override fun onBackPressed() {
//        navController?.navigateUp()
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false || super.onSupportNavigateUp()
    }

    fun getTarefa(descricaoTarefa: String) {
        val tarefa = Tarefa(
            descricao = descricaoTarefa,
            completa = false
        )
        listaTarefas.add(tarefa)
        salvarLista(listaTarefas)
        navController?.navigateUp()
    }

    private fun recuperaLista(chave: String) {
        val jsonString = sharedPrefs.getString(chave, "[]")
        val lista = gson.fromJson<Array<Tarefa>>(
            jsonString,
            Array<Tarefa>::class.java
        )

        listaTarefas.addAll(lista)
    }

    private fun salvarLista(lista: List<Tarefa>) {
        val jsonString = gson.toJson(lista)

        sharedPrefs
            .edit()
            .putString("TAREFAS", jsonString)
            .apply()
    }

//    private fun abrirTelaListaTarefas() {
//        val listaTarefasFragment = ListaTarefasFragment.newInstance({
//            abrirTelaCriarTarefas()
//        }, "")
//
//        supportFragmentManager.beginTransaction().replace(
//            binding.frameLayout.id,
//            listaTarefasFragment
//        ).commit()
//    }
//
//    private fun abrirTelaCriarTarefas() {
//        val listaTarefas = arrayListOf<Tarefa>()
//        val criaTarefasFragment = CriaTarefasFragment.newInstance({
//            println(categorizaTarefa(it))
//            val tarefa = Tarefa(
//                descricao = it,
//                completa = false
//            )
//            listaTarefas.add(tarefa)
//        }, "")
//
//        supportFragmentManager.beginTransaction().replace(
//            binding.frameLayout.id,
//            criaTarefasFragment
//        ).commit()
//    }

    private fun categorizaTarefa(tarefa: String): List<String> {
        val wordArray = tarefa.trim().split("\\s+".toRegex())
        val hashTagArray = arrayListOf<String>()
        for (i in wordArray) {
            if (i.contains("#")) {
                if (!hashTagArray.contains(i)) {
                    hashTagArray.add(i)
                }
            }
        }
        return hashTagArray
    }
}