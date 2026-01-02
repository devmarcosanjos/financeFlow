package com.marcosanjos.financeflow.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcosanjos.financeflow.R
import com.marcosanjos.financeflow.data.Despesa
import com.marcosanjos.financeflow.data.Receita
import com.marcosanjos.financeflow.data.Transacao
import java.text.NumberFormat
import java.util.Locale

class TransacaoAdapter(
    private val transacoes: List<Transacao>,
    private val onItemClick: (Transacao) -> Unit
) : RecyclerView.Adapter<TransacaoAdapter.TransacaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransacaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_despesa, parent, false)
        return TransacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransacaoViewHolder, position: Int) {
        val transacao = transacoes[position]
        holder.bind(transacao)
        holder.itemView.setOnClickListener { onItemClick(transacao) }
    }

    override fun getItemCount(): Int = transacoes.size

    class TransacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.tvNomeDespesa)
        private val categoriaTextView: TextView = itemView.findViewById(R.id.tvCategoriaDespesa)
        private val dataTextView: TextView = itemView.findViewById(R.id.tvDataDespesa) // Novo TextView para a data
        private val valorTextView: TextView = itemView.findViewById(R.id.tvValorDespesa)

        fun bind(transacao: Transacao) {
            nomeTextView.text = transacao.nome
            categoriaTextView.text = transacao.categoria
            dataTextView.text = transacao.data // Exibe a data

            // Corrigindo o warning do Locale
            val formatoMoeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

            when (transacao) {
                is Despesa -> {
                    valorTextView.text = "- ${formatoMoeda.format(transacao.valor)}"
                    valorTextView.setTextColor(Color.RED)
                }
                is Receita -> {
                    valorTextView.text = "+ ${formatoMoeda.format(transacao.valor)}"
                    valorTextView.setTextColor(Color.GREEN)
                }
            }
        }
    }
}