package com.marcosanjos.financeflow.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.marcosanjos.financeflow.R
import com.marcosanjos.financeflow.data.Receita

class ReceitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita)

        val nomeEditText = findViewById<EditText>(R.id.etNomeReceitaValue)
        val valorEditText = findViewById<EditText>(R.id.etValorReceita)
        val categoriaEditText = findViewById<EditText>(R.id.etCategoriaReceitaValue)
        val confirmarButton = findViewById<Button>(R.id.btnReceitaConfirmar)

        confirmarButton.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val valorStr = valorEditText.text.toString()
            val categoria = categoriaEditText.text.toString()

            val valor = valorStr.toDoubleOrNull() ?: 0.0

            if (nome.isNotEmpty() && valorStr.isNotEmpty() && categoria.isNotEmpty()) {
                val receita = Receita(nome = nome, valor = valor, categoria = categoria)

                val resultIntent = Intent().apply {
                    putExtra("NOVA_RECEITA", receita)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}