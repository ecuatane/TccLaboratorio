package com.example.tcc.Servicos;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.R;
import com.example.tcc.dados.Usuario;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViwHolder> {
private Context context;
private List<Usuario> list;

    public CustomAdapter(Context context, List<Usuario> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomViwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViwHolder((LayoutInflater.from(context).inflate(R.layout.single_item, parent, false)));

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViwHolder holder, int position) {
       holder.textViewUser.setText(list.get(position).getNome());
       holder.textSessao.setText(String.valueOf(list.get(position).getIdSessao()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
