package com.marcosanjos.financeflow

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marcosanjos.financeflow.FinanceDbHelper
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: FinanceDbHelper
    private val transacoes = mutableListOf<Transacao>()
    private lateinit var transacaoAdapter: TransacaoAdapter
    private lateinit var saldoTextView: TextView

    private var saldoAtual = 0.0

    // Launcher para a tela de Receita
    private val getReceitaResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val novaReceita = result.data?.getParcelableExtra("NOVA_RECEITA", Receita::class.java)
            if (novaReceita != null) {
                dbHelper.addTransacao(novaReceita)
                loadDataAndRefreshUI()
            }
        }
    }

    // Launcher para a tela de Despesa
    private val getDespesaResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val novaDespesa = result.data?.getParcelableExtra("NOVA_DESPESA", Despesa::class.java)
            if (novaDespesa != null) {
                dbHelper.addTransacao(novaDespesa)
                loadDataAndRefreshUI()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = FinanceDbHelper(this)

        saldoTextView = findViewById(R.id.tvSaldoValue)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDespesas)
        transacaoAdapter = TransacaoAdapter(transacoes)
        recyclerView.adapter = transacaoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadDataAndRefreshUI()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_receita -> {
                    val intent = Intent(this, receitaActivity::class.java)
                    getReceitaResult.launch(intent)
                    true
                }
                R.id.nav_despesa -> {
                    val intent = Intent(this, despesaActivity::class.java)
                    getDespesaResult.launch(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadDataAndRefreshUI() {
        val loadedTransacoes = dbHelper.getAllTransacoes()
        transacoes.clear()
        transacoes.addAll(loadedTransacoes)
        transacaoAdapter.notifyDataSetChanged()

        saldoAtual = 0.0
        transacoes.forEach { transacao ->
            when (transacao) {
                is Receita -> saldoAtual += transacao.valor
                is Despesa -> saldoAtual -= transacao.valor
            }
        }
        atualizarSaldoNaTela()
    }

    private fun atualizarSaldoNaTela() {
        val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        saldoTextView.text = formatoMoeda.format(saldoAtual)
    }
}