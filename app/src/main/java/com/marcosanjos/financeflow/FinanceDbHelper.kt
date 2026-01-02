package com.marcosanjos.financeflow

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FinanceDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // 1. Versão do banco incrementada para forçar a atualização da estrutura
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "financeflow.db"
        private const val TABLE_TRANSACOES = "transacoes"

        private const val KEY_ID = "id"
        private const val KEY_TIPO = "tipo"
        private const val KEY_NOME = "nome"
        private const val KEY_VALOR = "valor"
        private const val KEY_CATEGORIA = "categoria"

        // Constantes para os tipos de transação
        private const val TIPO_DESPESA = 1
        private const val TIPO_RECEITA = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // 2. A coluna tipo agora é INTEGER
        val createTableSQL = ("CREATE TABLE " + TABLE_TRANSACOES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TIPO + " INTEGER,"
                + KEY_NOME + " TEXT,"
                + KEY_VALOR + " REAL,"
                + KEY_CATEGORIA + " TEXT" + ")")
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Deleta a tabela antiga e cria uma nova. Perde os dados existentes.
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACOES")
        onCreate(db)
    }

    fun addTransacao(transacao: Transacao) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NOME, transacao.nome)
            put(KEY_VALOR, transacao.valor)
            put(KEY_CATEGORIA, transacao.categoria)
            // 3. Salva 1 para Despesa e 2 para Receita
            val tipo = if (transacao is Receita) TIPO_RECEITA else TIPO_DESPESA
            put(KEY_TIPO, tipo)
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
                // 4. Lê o tipo como um número (Int)
                val tipo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TIPO))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NOME))
                val valor = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_VALOR))
                val categoria = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORIA))

                // 5. Cria o objeto correto com base no número (1 ou 2)
                val transacao = if (tipo == TIPO_RECEITA) {
                    Receita(nome, valor, categoria)
                } else {
                    Despesa(nome, valor, categoria)
                }
                transacoesList.add(transacao)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return transacoesList
    }
}