package com.example.meufii;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AtivoAdapter extends RecyclerView.Adapter<AtivoAdapter.AtivoViewHolder>  {

    private List<Ativo> ativos;
    private ItemClickListener itemClickListener;

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

        private AtivoViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome);
            codigo = itemView.findViewById(R.id.codigo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public void setOnItemClickAtivo(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
