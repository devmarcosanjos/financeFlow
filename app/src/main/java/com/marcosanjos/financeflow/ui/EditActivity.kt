package com.marcosanjos.financeflow.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.marcosanjos.financeflow.R
import com.marcosanjos.financeflow.data.Despesa
import com.marcosanjos.financeflow.data.Receita
import com.marcosanjos.financeflow.data.Transacao
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        val categoriaSpinner = findViewById<Spinner>(R.id.spinnerCategoriaEdit)
        val dataEditText = findViewById<EditText>(R.id.etDataEdit)
        val tipoPagamentoSpinner = findViewById<Spinner>(R.id.spinnerTipoPagamentoEdit)
        val tipoPagamentoLabel = findViewById<TextView>(R.id.tvTipoPagamentoNameEdit)
        val atualizarButton = findViewById<Button>(R.id.btnAtualizar)
        val excluirButton = findViewById<Button>(R.id.btnExcluir)
        val voltarButton = findViewById<Button>(R.id.btnVoltar)

        transacaoOriginal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("TRANSACAO_PARA_EDITAR", Transacao::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra("TRANSACAO_PARA_EDITAR")
        }!!

        val (categoriaArrayRes, categorias) = if (transacaoOriginal is Despesa) {
            Pair(R.array.expense_categories, resources.getStringArray(R.array.expense_categories))
        } else {
            Pair(R.array.income_categories, resources.getStringArray(R.array.income_categories))
        }

        val categoriaAdapter = ArrayAdapter.createFromResource(
            this,
            categoriaArrayRes,
            android.R.layout.simple_spinner_item
        )
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoriaSpinner.adapter = categoriaAdapter
        val categoriaPosition = categorias.indexOf(transacaoOriginal.categoria)
        if (categoriaPosition >= 0) {
            categoriaSpinner.setSelection(categoriaPosition)
        }

        if (transacaoOriginal is Despesa) {
            tipoPagamentoLabel.visibility = View.VISIBLE
            tipoPagamentoSpinner.visibility = View.VISIBLE

            val paymentTypesAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.payment_types,
                android.R.layout.simple_spinner_item
            )
            paymentTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tipoPagamentoSpinner.adapter = paymentTypesAdapter

            val paymentTypes = resources.getStringArray(R.array.payment_types)
            val position = paymentTypes.indexOf((transacaoOriginal as Despesa).tipoPagamento)
            if (position >= 0) {
                tipoPagamentoSpinner.setSelection(position)
            }
        }

        nomeEditText.setText(transacaoOriginal.nome)
        valorEditText.setText(transacaoOriginal.valor.toString())
        dataEditText.setText(transacaoOriginal.data)

        // Configuração do DatePickerDialog
        val calendario = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendario.set(Calendar.YEAR, year)
            calendario.set(Calendar.MONTH, month)
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val formato = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(formato, Locale.US)
            dataEditText.setText(sdf.format(calendario.time))
        }

        dataEditText.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        atualizarButton.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val valorStr = valorEditText.text.toString()
            val categoria = categoriaSpinner.selectedItem.toString()
            val data = dataEditText.text.toString()

            val valor = valorStr.toDoubleOrNull() ?: 0.0

            if (nome.isNotEmpty() && valorStr.isNotEmpty()) {
                val transacaoEditada: Transacao = when (transacaoOriginal) {
                    is Receita -> Receita(transacaoOriginal.id, nome, valor, categoria, data)
                    is Despesa -> {
                        val tipoPagamento = tipoPagamentoSpinner.selectedItem.toString()
                        Despesa(transacaoOriginal.id, nome, valor, categoria, data, tipoPagamento)
                    }
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