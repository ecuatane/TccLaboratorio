package com.example.tcc.Servicos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.R;


public class CustomViwHolder extends RecyclerView.ViewHolder {

    public TextView textViewUser, textSessao;

    public CustomViwHolder(@NonNull View itemView) {
        super(itemView);
        textViewUser =itemView.findViewById(R.id.textName);
        textSessao =itemView.findViewById(R.id.textSessao);

    }
}
