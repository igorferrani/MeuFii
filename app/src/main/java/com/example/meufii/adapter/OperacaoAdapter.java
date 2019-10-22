package com.example.meufii.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.meufii.Interface.ItemClickOperacaoListener;
import com.example.meufii.R;
import com.example.meufii.model.Operacao;
import com.example.meufii.util.UtilFormat;

import java.util.List;

public class OperacaoAdapter extends RecyclerView.Adapter<OperacaoAdapter.AtivoViewHolder>  {

    private List<Operacao> itens;
    private ItemClickOperacaoListener itemClickOperacaoListener;

    public OperacaoAdapter(List<Operacao> itens) {
        this.itens = itens;
    }

    @NonNull
    @Override
    public AtivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operacao, parent, false);
        AtivoViewHolder holder = new AtivoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AtivoViewHolder holder, int position) {
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

    public class AtivoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView valorTotal;
        private TextView dataOperacao;
        private TextView quantidadeCotas;

        private AtivoViewHolder(@NonNull View itemView) {
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
