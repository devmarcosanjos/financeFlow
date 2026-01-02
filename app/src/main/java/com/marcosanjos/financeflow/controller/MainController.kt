package com.marcosanjos.financeflow.controller

import android.content.Context
import com.marcosanjos.financeflow.model.Despesa
import com.marcosanjos.financeflow.model.FinanceDbHelper
import com.marcosanjos.financeflow.model.Receita
import com.marcosanjos.financeflow.model.Transacao

class MainController(context: Context) {

    private val dbHelper = FinanceDbHelper(context)

    fun buscarTransacoes(): List<Transacao> {
        return dbHelper.getAllTransacoes()
    }

    fun adicionarTransacao(transacao: Transacao) {
        dbHelper.addTransacao(transacao)
    }

    fun calcularSaldo(transacoes: List<Transacao>): Double {
        var saldo = 0.0
        for (transacao in transacoes) {
            when (transacao) {
                is Receita -> saldo += transacao.valor
                is Despesa -> saldo -= transacao.valor
            }
        }
        return saldo
    }
}
