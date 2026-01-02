package com.marcosanjos.financeflow.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FinanceDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "financeflow.db"
        private const val TABLE_TRANSACOES = "transacoes"

        private const val KEY_ID = "id"
        private const val KEY_TIPO = "tipo"
        private const val KEY_NOME = "nome"
        private const val KEY_VALOR = "valor"
        private const val KEY_CATEGORIA = "categoria"

        private const val TIPO_DESPESA = 1
        private const val TIPO_RECEITA = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = ("CREATE TABLE " + TABLE_TRANSACOES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TIPO + " INTEGER,"
                + KEY_NOME + " TEXT,"
                + KEY_VALOR + " REAL,"
                + KEY_CATEGORIA + " TEXT" + ")")
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
            val tipo = if (transacao is Receita) TIPO_RECEITA else TIPO_DESPESA
            put(KEY_TIPO, tipo)
        }
        db.insert(TABLE_TRANSACOES, null, values)
        db.close()
    }

    fun updateTransacao(transacao: Transacao): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NOME, transacao.nome)
            put(KEY_VALOR, transacao.valor)
            put(KEY_CATEGORIA, transacao.categoria)
        }
        val success = db.update(TABLE_TRANSACOES, values, "$KEY_ID = ?", arrayOf(transacao.id.toString()))
        db.close()
        return success
    }

    fun deleteTransacao(id: Long): Int {
        val db = this.writableDatabase
        // Deleta a linha onde o ID corresponde
        val success = db.delete(TABLE_TRANSACOES, "$KEY_ID = ?", arrayOf(id.toString()))
        db.close()
        return success
    }

    fun getAllTransacoes(): List<Transacao> {
        val transacoesList = mutableListOf<Transacao>()
        val selectQuery = "SELECT * FROM $TABLE_TRANSACOES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(KEY_ID))
                val tipo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_TIPO))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NOME))
                val valor = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_VALOR))
                val categoria = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORIA))

                val transacao = if (tipo == TIPO_RECEITA) {
                    Receita(id, nome, valor, categoria)
                } else {
                    Despesa(id, nome, valor, categoria)
                }
                transacoesList.add(transacao)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return transacoesList
    }
}