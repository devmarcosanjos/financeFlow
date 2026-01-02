package com.marcosanjos.financeflow.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.marcosanjos.financeflow.R
import com.marcosanjos.financeflow.data.Despesa
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DespesaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despesa)

        val nomeEditText = findViewById<EditText>(R.id.etNomeDespesaValue)
        val valorEditText = findViewById<EditText>(R.id.editTextNumberDecimal)
        val categoriaSpinner = findViewById<Spinner>(R.id.spinnerCategoria)
        val dataEditText = findViewById<EditText>(R.id.etDataDespesa)
        val tipoPagamentoSpinner = findViewById<Spinner>(R.id.spinnerTipoPagamento)
        val confirmarButton = findViewById<Button>(R.id.btnDespesaConfirmar)

        // Adapter para o spinner de categorias
        val categoriaAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.expense_categories,
            android.R.layout.simple_spinner_item
        )
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoriaSpinner.adapter = categoriaAdapter

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.payment_types,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipoPagamentoSpinner.adapter = adapter

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

        confirmarButton.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val valorStr = valorEditText.text.toString()
            val categoria = categoriaSpinner.selectedItem.toString()
            val data = dataEditText.text.toString()
            val tipoPagamento = tipoPagamentoSpinner.selectedItem.toString()

            val valor = valorStr.toDoubleOrNull() ?: 0.0

            if (nome.isNotEmpty() && valorStr.isNotEmpty() && data.isNotEmpty()) {
                val despesa = Despesa(
                    nome = nome,
                    valor = valor,
                    categoria = categoria,
                    data = data,
                    tipoPagamento = tipoPagamento
                )

                val resultIntent = Intent().apply {
                    putExtra("NOVA_DESPESA", despesa)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
                val currentDate = sdf.format(Date())
                val despesa = Despesa(
                    nome = nome,
                    valor = valor,
                    categoria = categoria,
                    data = currentDate,
                    tipoPagamento = tipoPagamento
                )

                val resultIntent = Intent().apply {
                    putExtra("NOVA_DESPESA", despesa)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}