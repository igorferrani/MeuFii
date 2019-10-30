package com.br.meufii.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.meufii.interfaceApp.ItemClickAtivoListener;
import com.br.meufii.R;
import com.br.meufii.model.Ativo;

import java.util.List;

public class BuscaAtivoAdapter extends RecyclerView.Adapter<BuscaAtivoAdapter.AtivoViewHolder>  {

    private List<Ativo> itens;
    private ItemClickAtivoListener itemClickListener;

    public BuscaAtivoAdapter(List<Ativo> itens) {
        this.itens = itens;
    }

    @NonNull
    @Override
    public AtivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_busca_ativo, parent, false);
        AtivoViewHolder holder = new AtivoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AtivoViewHolder holder, int position) {
        holder.codigo.setText(itens.get(position).getCodigo());
    }

    @Override
    public int getItemCount() {
        return itens == null ? 0 : itens.size();
    }

    public void setItens(List<Ativo> itens) {
        this.itens = itens;
        notifyDataSetChanged();
    }

    public class AtivoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView codigo;

        private AtivoViewHolder(@NonNull View itemView) {
            super(itemView);

            codigo = itemView.findViewById(R.id.codigo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClickAtivo(itens.get(getAdapterPosition()));
            }
        }
    }

    public void setOnItemClick(ItemClickAtivoListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
