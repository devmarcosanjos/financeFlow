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
import com.marcosanjos.financeflow.data.Receita
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ReceitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita)

        val nomeEditText = findViewById<EditText>(R.id.etNomeReceitaValue)
        val valorEditText = findViewById<EditText>(R.id.etValorReceita)
        val categoriaSpinner = findViewById<Spinner>(R.id.spinnerCategoriaReceita)
        val dataEditText = findViewById<EditText>(R.id.etDataReceita)
        val confirmarButton = findViewById<Button>(R.id.btnReceitaConfirmar)

        // Adapter para o spinner de categorias
        val categoriaAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.income_categories,
            android.R.layout.simple_spinner_item
        )
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoriaSpinner.adapter = categoriaAdapter

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

            val valor = valorStr.toDoubleOrNull() ?: 0.0

            if (nome.isNotEmpty() && valorStr.isNotEmpty() && data.isNotEmpty()) {
                val receita = Receita(nome = nome, valor = valor, categoria = categoria, data = data)

                val resultIntent = Intent().apply {
                    putExtra("NOVA_RECEITA", receita)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
                val currentDate = sdf.format(Date())
                val receita = Receita(nome = nome, valor = valor, categoria = categoria, data = currentDate)

                val resultIntent = Intent().apply {
                    putExtra("NOVA_RECEITA", receita)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}