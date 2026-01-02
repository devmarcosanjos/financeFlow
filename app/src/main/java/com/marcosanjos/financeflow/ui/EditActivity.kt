package com.marcosanjos.financeflow.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.marcosanjos.financeflow.R
import com.marcosanjos.financeflow.data.Despesa
import com.marcosanjos.financeflow.data.Receita
import com.marcosanjos.financeflow.data.Transacao

class EditActivity : AppCompatActivity() {

    private lateinit var transacaoOriginal: Transacao

    companion object {
        const val RESULT_DELETE = 99
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val nomeEditText = findViewById<EditText>(R.id.etNomeEditValue)
        val valorEditText = findViewById<EditText>(R.id.etValorEdit)
        val categoriaEditText = findViewById<EditText>(R.id.etCategoriaEditValue)
        val atualizarButton = findViewById<Button>(R.id.btnAtualizar)
        val excluirButton = findViewById<Button>(R.id.btnExcluir)
        val voltarButton = findViewById<Button>(R.id.btnVoltar)

        // Corrigindo o warning do getParcelableExtra
        transacaoOriginal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("TRANSACAO_PARA_EDITAR", Transacao::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra("TRANSACAO_PARA_EDITAR")
        }!!

        nomeEditText.setText(transacaoOriginal.nome)
        valorEditText.setText(transacaoOriginal.valor.toString())
        categoriaEditText.setText(transacaoOriginal.categoria)

        atualizarButton.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val valorStr = valorEditText.text.toString()
            val categoria = categoriaEditText.text.toString()

            val valor = valorStr.toDoubleOrNull() ?: 0.0

            if (nome.isNotEmpty() && valorStr.isNotEmpty() && categoria.isNotEmpty()) {
                val transacaoEditada: Transacao = when (transacaoOriginal) {
                    is Receita -> Receita(transacaoOriginal.id, nome, valor, categoria)
                    is Despesa -> Despesa(transacaoOriginal.id, nome, valor, categoria)
                }

                val resultIntent = Intent().apply {
                    putExtra("TRANSACAO_EDITADA", transacaoEditada)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        excluirButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("TRANSACAO_ID_PARA_DELETAR", transacaoOriginal.id)
            }
            setResult(RESULT_DELETE, resultIntent)
            finish()
        }

        voltarButton.setOnClickListener {
            finish()
        }
    }
}