package com.marcosanjos.financeflow.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marcosanjos.financeflow.R
import com.marcosanjos.financeflow.data.FinanceDbHelper
import com.marcosanjos.financeflow.data.Receita
import com.marcosanjos.financeflow.data.Despesa
import com.marcosanjos.financeflow.data.Transacao
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: FinanceDbHelper
    private val transacoes = mutableListOf<Transacao>()
    private lateinit var transacaoAdapter: TransacaoAdapter
    private lateinit var saldoTextView: TextView

    private var saldoAtual = 0.0

    private val getReceitaResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getParcelableExtra("NOVA_RECEITA", Receita::class.java)?.let {
                dbHelper.addTransacao(it)
                loadDataAndRefreshUI()
            }
        }
    }

    private val getDespesaResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getParcelableExtra("NOVA_DESPESA", Despesa::class.java)?.let {
                dbHelper.addTransacao(it)
                loadDataAndRefreshUI()
            }
        }
    }

    private val getEditResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val transacaoEditada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("TRANSACAO_EDITADA", Transacao::class.java)
                } else {
                    @Suppress("DEPRECATION") result.data?.getParcelableExtra("TRANSACAO_EDITADA")
                }
                if (transacaoEditada != null) {
                    dbHelper.updateTransacao(transacaoEditada)
                    loadDataAndRefreshUI()
                }
            }
            EditActivity.RESULT_DELETE -> {
                val transacaoId = result.data?.getLongExtra("TRANSACAO_ID_PARA_DELETAR", -1L)
                if (transacaoId != null && transacaoId != -1L) {
                    dbHelper.deleteTransacao(transacaoId)
                    loadDataAndRefreshUI()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = FinanceDbHelper(this)
        saldoTextView = findViewById(R.id.tvSaldoValue)

        transacaoAdapter = TransacaoAdapter(transacoes) { transacao ->
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra("TRANSACAO_PARA_EDITAR", transacao)
            }
            getEditResult.launch(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDespesas)
        recyclerView.adapter = transacaoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadDataAndRefreshUI()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_receita -> {
                    val intent = Intent(this, ReceitaActivity::class.java)
                    getReceitaResult.launch(intent)
                    true
                }
                R.id.nav_despesa -> {
                    val intent = Intent(this, DespesaActivity::class.java)
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
        val formatoMoeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
        saldoTextView.text = formatoMoeda.format(saldoAtual)
    }
}