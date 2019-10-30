package com.br.meufii.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.meufii.interfaceApp.ItemClickCardResumoAtivoListener;
import com.br.meufii.model.CardResumoAtivo;
import com.br.meufii.R;

import java.util.List;

public class AtivoAdapter extends RecyclerView.Adapter<AtivoAdapter.AtivoViewHolder>  {

    private List<CardResumoAtivo> itens;
    private ItemClickCardResumoAtivoListener itemClickCardResumoAtivoListener;

    public AtivoAdapter(List<CardResumoAtivo> itens) {
        this.itens = itens;
    }

    @NonNull
    @Override
    public AtivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ativo, parent, false);
        AtivoViewHolder holder = new AtivoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AtivoViewHolder holder, int position) {
        holder.nome.setText(itens.get(position).getNome());
        holder.codigo.setText(itens.get(position).getCodigo());
        holder.valorInvestido.setText(itens.get(position).getValorInvestidoFormatado());
        holder.quantidadeCotas.setText(Integer.toString(itens.get(position).getQuantidadeCotas()));
    }

    @Override
    public int getItemCount() {
        return itens == null ? 0 : itens.size();
    }

    public void setItens(List<CardResumoAtivo> itens) {
        this.itens = itens;
        notifyDataSetChanged();
    }

    public class AtivoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nome;
        private TextView codigo;
        private TextView valorInvestido;
        private TextView valorRetornado;
        private TextView quantidadeCotas;

        private AtivoViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome);
            codigo = itemView.findViewById(R.id.codigo);
            valorInvestido = itemView.findViewById(R.id.valor_investido);
            valorRetornado = itemView.findViewById(R.id.valor_retornado);
            quantidadeCotas = itemView.findViewById(R.id.quantidade_cotas);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickCardResumoAtivoListener != null) {
                itemClickCardResumoAtivoListener.onItemClickCardResumoAtivo(itens.get(getAdapterPosition()));
            }
        }
    }

    public void setOnItemClick(ItemClickCardResumoAtivoListener itemClickCardResumoAtivoListener){
        this.itemClickCardResumoAtivoListener = itemClickCardResumoAtivoListener;
    }
}
