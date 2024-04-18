package com.example.minhastarefas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minhastarefas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        abrirTelaListaTarefas()
    }

    private fun abrirTelaListaTarefas() {
        val listaTarefasFragment = ListaTarefasFragment.newInstance({
            abrirTelaCriarTarefas()
        }, "")

        supportFragmentManager.beginTransaction().replace(
            binding.frameLayout.id,
            listaTarefasFragment
        ).commit()
    }

    private fun abrirTelaCriarTarefas() {
        val listaTarefas = arrayListOf<Tarefa>()
        val criaTarefasFragment = CriaTarefasFragment.newInstance({
            println(categorizaTarefa(it))
            val tarefa = Tarefa(
                descricao = it,
                completa = false
            )
            listaTarefas.add(tarefa)
        }, "")

        supportFragmentManager.beginTransaction().replace(
            binding.frameLayout.id,
            criaTarefasFragment
        ).commit()
    }

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