package com.br.meufii.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.meufii.R;
import com.br.meufii.interfaceApp.ItemClickOperacaoListener;
import com.br.meufii.model.Operacao;
import com.br.meufii.util.UtilFormat;

import java.util.List;

public class UltimasOperacoesAdapter extends RecyclerView.Adapter<UltimasOperacoesAdapter.UltimasOperacoesViewHolder>  {

    private List<Operacao> itens;
    private ItemClickOperacaoListener itemClickOperacaoListener;

    public UltimasOperacoesAdapter(List<Operacao> itens) {
        this.itens = itens;
    }

    @NonNull
    @Override
    public UltimasOperacoesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ultimas_operacoes, parent, false);
        return new UltimasOperacoesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UltimasOperacoesViewHolder holder, int position) {
        holder.valorTotal.setText(UtilFormat.formatDecimal(itens.get(position).valorTotalOperacao()));
        holder.dataOperacao.setText(itens.get(position).getData());
        holder.quantidadeCotas.setText(itens.get(position).getQuantidadeCotas() + " cotas");
    }

    @Override
    public int getItemCount() {
        return itens == null ? 0 : itens.size();
    }

    public void setItens(List<Operacao> itens) {
        this.itens = itens;
        notifyDataSetChanged();
    }

    public class UltimasOperacoesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView valorTotal;
        private TextView dataOperacao;
        private TextView quantidadeCotas;

        private UltimasOperacoesViewHolder(@NonNull View itemView) {
            super(itemView);

            valorTotal = itemView.findViewById(R.id.valor_total);
            dataOperacao = itemView.findViewById(R.id.data_operacao);
            quantidadeCotas = itemView.findViewById(R.id.quantidade_cotas);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickOperacaoListener != null) {
                itemClickOperacaoListener.onItemClickOperacao(itens.get(getAdapterPosition()));
            }
        }
    }

    public void setOnItemClick(ItemClickOperacaoListener itemClickOperacaoListener){
        this.itemClickOperacaoListener = itemClickOperacaoListener;
    }
}
