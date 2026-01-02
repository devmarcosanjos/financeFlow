package com.marcosanjos.financeflow.controller

import com.marcosanjos.financeflow.model.Despesa

class DespesaController {

    fun validarECriarDespesa(nome: String, valorStr: String, categoria: String, tipoPagamento: String, data: Long): Despesa? {
        val valor = valorStr.toDoubleOrNull() ?: 0.0
        if (nome.isNotEmpty() && valorStr.isNotEmpty() && categoria.isNotEmpty()) {
            return Despesa(nome, valor, categoria, tipoPagamento, data)
        }
        return null
    }
}
