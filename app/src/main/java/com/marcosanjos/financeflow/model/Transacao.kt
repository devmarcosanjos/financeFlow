package com.marcosanjos.financeflow.model

import android.os.Parcelable

sealed interface Transacao : Parcelable {
    val nome: String
    val valor: Double
    val categoria: String
    val data: Long
}