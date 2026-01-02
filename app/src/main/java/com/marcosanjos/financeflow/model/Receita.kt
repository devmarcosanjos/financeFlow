package com.marcosanjos.financeflow.model

import com.marcosanjos.financeflow.model.Transacao
import kotlinx.parcelize.Parcelize

@Parcelize
data class Receita(
    override val nome: String,
    override val valor: Double,
    override val categoria: String,
    override val data: Long
) : Transacao