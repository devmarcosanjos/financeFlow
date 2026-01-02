package com.marcosanjos.financeflow

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class receitaActivity : AppCompatActivity() {
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
                val receita = Receita(nome, valor, categoria)

                val resultIntent = Intent().apply {
                    putExtra("NOVA_RECEITA", receita) // Voltando a usar a chave específica
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}