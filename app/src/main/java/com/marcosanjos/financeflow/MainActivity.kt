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
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val transacoes = mutableListOf<Transacao>()
    private lateinit var transacaoAdapter: TransacaoAdapter
    private lateinit var saldoTextView: TextView

    private var saldoAtual = 0.0

    // Launcher único para obter o resultado de qualquer transação
    private val getTransacaoResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val novaTransacao = result.data?.getParcelableExtra("NOVA_TRANSACAO", Transacao::class.java)
            if (novaTransacao != null) {
                transacoes.add(novaTransacao)
                transacaoAdapter.notifyItemInserted(transacoes.size - 1)

                // Atualiza o saldo com base no tipo de transação
                when (novaTransacao) {
                    is Receita -> saldoAtual += novaTransacao.valor
                    is Despesa -> saldoAtual -= novaTransacao.valor
                }
                atualizarSaldoNaTela()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saldoTextView = findViewById(R.id.tvSaldoValue)
        atualizarSaldoNaTela()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDespesas)
        transacaoAdapter = TransacaoAdapter(transacoes)
        recyclerView.adapter = transacaoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_receita -> {
                    val intent = Intent(this, receitaActivity::class.java)
                    getTransacaoResult.launch(intent)
                    true
                }
                R.id.nav_despesa -> {
                    val intent = Intent(this, despesaActivity::class.java)
                    getTransacaoResult.launch(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun atualizarSaldoNaTela() {
        val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        saldoTextView.text = formatoMoeda.format(saldoAtual)
    }
}