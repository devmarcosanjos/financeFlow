package com.marcosanjos.financeflow

import kotlinx.parcelize.Parcelize

@Parcelize
data class Despesa(
    override val nome: String,
    override val valor: Double,
    override val categoria: String
) : Transacao
