package com.example.tcc.actividades;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.R;
import com.example.tcc.Servicos.CustomAdapter;
import com.example.tcc.dados.BancoDados;
import com.example.tcc.dados.Usuario;

import java.util.List;

public class ListarDadosUsuario extends FragmentActivity {

    RecyclerView recyclerView;
    List<Usuario> myUserList;
    CustomAdapter customAdapter;
    BancoDados banco;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vistausuario);
        banco = new BancoDados(getBaseContext());
        viewItens();
    }


    private void viewItens(){
        recyclerView = findViewById(R.id.recycle_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        myUserList = banco.getAllUsers();

        customAdapter = new CustomAdapter(this, myUserList);
        recyclerView.setAdapter(customAdapter);
    }
}
