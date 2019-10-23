package com.example.meufii.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meufii.interfaceApp.ItemClickAtivoListener;
import com.example.meufii.model.Ativo;
import com.example.meufii.R;

import java.util.List;

public class AtivoAdapter extends RecyclerView.Adapter<AtivoAdapter.AtivoViewHolder>  {

    private List<Ativo> ativos;
    private ItemClickAtivoListener itemClickAtivoListener;

    public AtivoAdapter(List<Ativo> ativos) {
        this.ativos = ativos;
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
        holder.nome.setText(ativos.get(position).getNome());
        holder.codigo.setText(ativos.get(position).getCodigo());
        holder.valorInvestido.setText(ativos.get(position).getValorInvestidoFormatado());
        holder.quantidadeCotas.setText(Integer.toString(ativos.get(position).getQuantidadeCotas()));
    }

    @Override
    public int getItemCount() {
        return ativos == null ? 0 : ativos.size();
    }

    public void setAtivos(List<Ativo> ativos) {
        this.ativos = ativos;
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
            if (itemClickAtivoListener != null) {
                itemClickAtivoListener.onItemClickAtivo(ativos.get(getAdapterPosition()));
            }
        }
    }

    public void setOnItemClick(ItemClickAtivoListener itemClickAtivoListener){
        this.itemClickAtivoListener = itemClickAtivoListener;
    }
}
