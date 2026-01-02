package com.marcosanjos.financeflow

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class TransacaoAdapter(private val transacoes: List<Transacao>) : RecyclerView.Adapter<TransacaoAdapter.TransacaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransacaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_despesa, parent, false)
        return TransacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransacaoViewHolder, position: Int) {
        val transacao = transacoes[position]
        holder.bind(transacao)
    }

    override fun getItemCount(): Int = transacoes.size

    class TransacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.tvNomeDespesa)
        private val categoriaTextView: TextView = itemView.findViewById(R.id.tvCategoriaDespesa)
        private val valorTextView: TextView = itemView.findViewById(R.id.tvValorDespesa)

        fun bind(transacao: Transacao) {
            nomeTextView.text = transacao.nome
            categoriaTextView.text = transacao.categoria

            val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

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
