package com.marcosanjos.financeflow

import android.os.Parcelable

sealed interface Transacao : Parcelable {
    val nome: String
    val valor: Double
    val categoria: String
}