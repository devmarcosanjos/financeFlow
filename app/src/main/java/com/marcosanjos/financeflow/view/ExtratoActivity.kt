package com.marcosanjos.financeflow.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcosanjos.financeflow.R
import com.marcosanjos.financeflow.controller.MainController
import java.text.NumberFormat
import java.util.Locale


import android.app.DatePickerDialog
import android.widget.Button
import com.marcosanjos.financeflow.model.Despesa
import com.marcosanjos.financeflow.model.Receita
import com.marcosanjos.financeflow.model.Transacao
import java.util.Calendar

class ExtratoActivity : AppCompatActivity() {

    private lateinit var controller: MainController
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransacaoAdapter
    private lateinit var tvSaldoValue: TextView
    private lateinit var btnReceitas: Button
    private lateinit var btnDespesas: Button
    private lateinit var btnMes: Button
    private lateinit var btnTodos: Button

    private var allTransacoes = listOf<Transacao>()
    private var filteredTransacoes = mutableListOf<Transacao>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato)

        controller = MainController(this)
        
        tvSaldoValue = findViewById(R.id.tvSaldoValue)
        recyclerView = findViewById(R.id.recyclerViewTransacoes)
        btnReceitas = findViewById(R.id.btnFiltroReceitas)
        btnDespesas = findViewById(R.id.btnFiltroDespesas)
        btnMes = findViewById(R.id.btnFiltroMes)
        btnTodos = findViewById(R.id.btnFiltroTodos)
        
        recyclerView.layoutManager = LinearLayoutManager(this)

        setupListeners()
        loadData()
    }

    private fun setupListeners() {
        btnTodos.setOnClickListener {
            resetFilter()
        }

        btnReceitas.setOnClickListener {
            filterByType(Receita::class.java)
        }

        btnDespesas.setOnClickListener {
            filterByType(Despesa::class.java)
        }

        btnMes.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, _ -> 
                filterByMonth(month, year)
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun loadData() {
        allTransacoes = controller.buscarTransacoes()
        resetFilter()
    }

    private fun resetFilter() {
        filteredTransacoes.clear()
        filteredTransacoes.addAll(allTransacoes)
        updateUI()
    }

    private fun filterByType(type: Class<out Transacao>) {
        filteredTransacoes.clear()
        filteredTransacoes.addAll(allTransacoes.filter { type.isInstance(it) })
        updateUI()
    }

    private fun filterByMonth(month: Int, year: Int) {
        filteredTransacoes.clear()
        filteredTransacoes.addAll(allTransacoes.filter { 
            val c = Calendar.getInstance()
            c.timeInMillis = it.data
            c.get(Calendar.MONTH) == month && c.get(Calendar.YEAR) == year
        })
        updateUI()
    }

    private fun updateUI() {
        adapter = TransacaoAdapter(filteredTransacoes) { _ -> }
        recyclerView.adapter = adapter
        
        val saldo = controller.calcularSaldo(filteredTransacoes)
        val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        tvSaldoValue.text = formatoMoeda.format(saldo)
    }
}
