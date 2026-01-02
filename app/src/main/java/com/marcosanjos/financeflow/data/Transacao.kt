package com.marcosanjos.financeflow.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface Transacao : Parcelable {
    val id: Long?
    val nome: String
    val valor: Double
    val categoria: String
    val data: String // Novo campo de data
}

@Parcelize
data class Despesa(
    override val id: Long? = null,
    override val nome: String,
    override val valor: Double,
    override val categoria: String,
    override val data: String, // Campo de data adicionado
    val tipoPagamento: String
) : Transacao

@Parcelize
data class Receita(
    override val id: Long? = null,
    override val nome: String,
    override val valor: Double,
    override val categoria: String,
    override val data: String // Campo de data adicionado
) : Transacao