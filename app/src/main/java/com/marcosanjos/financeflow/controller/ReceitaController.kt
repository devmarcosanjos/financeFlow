package com.marcosanjos.financeflow.controller

import com.marcosanjos.financeflow.model.Receita

class ReceitaController {

    fun validarECriarReceita(nome: String, valorStr: String, categoria: String, data: Long = System.currentTimeMillis()): Receita? {
        val valor = valorStr.toDoubleOrNull() ?: 0.0
        if (nome.isNotEmpty() && valorStr.isNotEmpty() && categoria.isNotEmpty()) {
            return Receita(nome, valor, categoria, data)
        }
        return null
    }
}
