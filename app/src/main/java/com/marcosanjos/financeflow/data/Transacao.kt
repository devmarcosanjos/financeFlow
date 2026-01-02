package com.marcosanjos.financeflow.data

import android.os.Parcelable

// Agora, toda transação obrigatoriamente terá um ID.
// Ele é nullable (Long?) porque uma transação nova, antes de ser salva,
// ainda não tem um ID.
sealed interface Transacao : Parcelable {
    val id: Long?
    val nome: String
    val valor: Double
    val categoria: String
}