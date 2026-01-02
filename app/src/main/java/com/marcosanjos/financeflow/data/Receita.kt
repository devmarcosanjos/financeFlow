package com.marcosanjos.financeflow.data

import kotlinx.parcelize.Parcelize

@Parcelize
data class Receita(
    // O id é nulo por padrão, pois uma receita nova ainda não tem id.
    override val id: Long? = null,
    override val nome: String,
    override val valor: Double,
    override val categoria: String
) : Transacao