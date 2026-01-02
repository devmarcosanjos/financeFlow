package com.marcosanjos.financeflow.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FinanceDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 4
        private const val DATABASE_NAME = "financeflow.db"
        private const val TABLE_TRANSACOES = "transacoes"

        private const val KEY_ID = "id"
        private const val KEY_TIPO = "tipo"
        private const val KEY_NOME = "nome"
        private const val KEY_VALOR = "valor"
        private const val KEY_CATEGORIA = "categoria"
        private const val KEY_DATA = "data"
        private const val KEY_TIPO_PAGAMENTO = "tipo_pagamento" // Crédito, Débito, Dinheiro

        // Constantes para os tipos de transação
        private const val TIPO_DESPESA = 1
        private const val TIPO_RECEITA = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = ("CREATE TABLE " + TABLE_TRANSACOES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TIPO + " INTEGER,"
                + KEY_NOME + " TEXT,"
                + KEY_VALOR + " REAL,"
                + KEY_CATEGORIA + " TEXT,"
                + KEY_DATA + " INTEGER,"
                + KEY_TIPO_PAGAMENTO + " TEXT" + ")")
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACOES")
        onCreate(db)
    }

    fun addTransacao(transacao: Transacao) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NOME, transacao.nome)
            put(KEY_VALOR, transacao.valor)
            put(KEY_CATEGORIA, transacao.categoria)
            put(KEY_DATA, transacao.data)
            
            val tipo = if (transacao is Receita) TIPO_RECEITA else TIPO_DESPESA
            put(KEY_TIPO, tipo)

            if (transacao is Despesa) {
                put(KEY_TIPO_PAGAMENTO, transacao.tipoPagamento)
            }
        }
        db.insert(TABLE_TRANSACOES, null, values)
        db.close()
    }

    fun getAllTransacoes(): List<Transacao> {
        val transacoesList = mutableListOf<Transacao>()
        val selectQuery = "SELECT * FROM $TABLE_TRANSACOES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val tipo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TIPO))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NOME))
                val valor = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_VALOR))
                val categoria = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORIA))
                val data = cursor.getLong(cursor.getColumnIndexOrThrow(KEY_DATA))
                
                val transacao = if (tipo == TIPO_RECEITA) {
                    Receita(nome, valor, categoria, data)
                } else {
                    // Se não tiver a coluna (banco antigo ou nulo), retorna string vazia ou default
                    val tipoPagamentoIndex = cursor.getColumnIndex(KEY_TIPO_PAGAMENTO)
                    val tipoPagamento = if (tipoPagamentoIndex != -1 && !cursor.isNull(tipoPagamentoIndex)) {
                        cursor.getString(tipoPagamentoIndex)
                    } else {
                        "Dinheiro"
                    }
                    Despesa(nome, valor, categoria, tipoPagamento, data)
                }
                transacoesList.add(transacao)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return transacoesList
    }
}