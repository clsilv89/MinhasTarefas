package com.example.minhastarefas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.minhastarefas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private val listaTarefas = arrayListOf<Tarefa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
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
        navController?.navigateUp()
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
        for(i in wordArray) {
            if(i.contains("#")) {
                if(!hashTagArray.contains(i)) {
                    hashTagArray.add(i)
                }
            }
        }
        return hashTagArray
    }
}