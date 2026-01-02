package com.marcosanjos.financeflow.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.marcosanjos.financeflow.R
import com.marcosanjos.financeflow.controller.DespesaController
import com.marcosanjos.financeflow.controller.MainController
import com.marcosanjos.financeflow.controller.ReceitaController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var mainController: MainController
    private val receitaController = ReceitaController()
    private val despesaController = DespesaController()
    
    // UI Components
    private lateinit var rgTipo: RadioGroup
    private lateinit var etValor: EditText
    private lateinit var etDescricao: EditText
    private lateinit var spCategoria: Spinner
    private lateinit var tvDataValue: TextView
    private lateinit var btnLancar: Button
    private lateinit var btnVerLancamentos: Button
    private lateinit var spTipoPagamento: Spinner


    private var dataSelecionada: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainController = MainController(this)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        rgTipo = findViewById(R.id.rgTipo)
        etValor = findViewById(R.id.etValorMain)
        etDescricao = findViewById(R.id.etDescricaoMain)
        spCategoria = findViewById(R.id.spCategoriaMain)
        tvDataValue = findViewById(R.id.tvDataValueMain)
        btnLancar = findViewById(R.id.btnLancar)
        btnVerLancamentos = findViewById(R.id.btnVerLancamentos)
        spTipoPagamento = findViewById(R.id.spTipoPagamentoMain)

        // Configurar data inicial
        val calendario = Calendar.getInstance()
        atualizarDataTexto(calendario)
    }

    private fun setupListeners() {
        // Toggle Tipo Pagamento Visibility
        rgTipo.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbDespesa) {
                spTipoPagamento.visibility = View.VISIBLE
                val adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.despesa_categorias,
                    android.R.layout.simple_spinner_item
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spCategoria.adapter = adapter
            } else {
                spTipoPagamento.visibility = View.GONE
                val adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.receita_categorias,
                    android.R.layout.simple_spinner_item
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spCategoria.adapter = adapter
            }
        }

        // Date Picker
        tvDataValue.setOnClickListener {
            val calendario = Calendar.getInstance()
            calendario.timeInMillis = dataSelecionada
            
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendario.set(year, month, dayOfMonth)
                    dataSelecionada = calendario.timeInMillis
                    atualizarDataTexto(calendario)
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Lançar Button
        btnLancar.setOnClickListener {
            lancarTransacao()
        }

        // Ver Lançamentos Button
        btnVerLancamentos.setOnClickListener {
            startActivity(Intent(this, ExtratoActivity::class.java))
        }
    }

    private fun lancarTransacao() {
        val valorStr = etValor.text.toString()
        val descricao = etDescricao.text.toString() // Mapped to Nome
        val categoria = spCategoria.selectedItem.toString()

        if (valorStr.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        if (rgTipo.checkedRadioButtonId == R.id.rbReceita) {
            val receita = receitaController.validarECriarReceita(descricao, valorStr, categoria, dataSelecionada)
            if (receita != null) {
                mainController.adicionarTransacao(receita)
                Toast.makeText(this, "Receita lançada com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos()
            } else {
                Toast.makeText(this, "Erro ao validar receita", Toast.LENGTH_SHORT).show()
            }
        } else {
            val tipoPagamento = spTipoPagamento.selectedItem.toString()
            val despesa = despesaController.validarECriarDespesa(descricao, valorStr, categoria, tipoPagamento, dataSelecionada)
            if (despesa != null) {
                mainController.adicionarTransacao(despesa)
                Toast.makeText(this, "Despesa lançada com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos()
            } else {
                Toast.makeText(this, "Erro ao validar despesa", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun limparCampos() {
        etValor.text.clear()
        etDescricao.text.clear()
        // Data mantém a selecionada ou reseta para hoje? Melhor manter a data pra facilitar lançamentos múltiplos.
        etDescricao.requestFocus()
    }

    private fun atualizarDataTexto(calendario: Calendar) {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        tvDataValue.text = formato.format(calendario.time)
    }
}