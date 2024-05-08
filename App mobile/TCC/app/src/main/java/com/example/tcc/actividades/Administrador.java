package com.example.tcc.actividades;


import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;


import com.example.tcc.R;
import com.example.tcc.dados.BancoDados;
import com.example.tcc.dados.Caracter;
import com.example.tcc.dados.Usuario;


import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Administrador extends FragmentActivity {

    BancoDados banco;
    public static final String head ="Nome"+","+"idSessao"+","+"Repeticao"+","+"Chave"+","+"Tp"+","+"Ts" +"\n";
    public static final String Headeruser = "Nome"+","+"idUsuario"+","+"Sexo"+","+"Faixa\n";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        Button btngerar = (Button) findViewById(R.id.butttonGerar);
        Button btngerarUser =(Button) findViewById(R.id.butttonGerar2);
        Button btnestatistica =(Button) findViewById(R.id.buttonstatisticas);

        Button btntest = (Button) findViewById(R.id.btntestar);
        TextView tvDat = (TextView) findViewById(R.id.idtxt);
        
        banco = new BancoDados(getBaseContext());
        Intent intent = getIntent();
        String dado = tvDat.getText() + String.valueOf(banco.getAllUsers().size());
        tvDat.setText(dado);

        String nome = intent.getExtras().getString("nome");

        // Aqui serao gerados os CSVs

        btnestatistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Administrador.this, ListarDadosUsuario.class);
                startActivity(i);
            }
        });

        btngerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarCSVData(banco);
            }
        });

        btngerarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarcsvuser(banco);
            }
        });


        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Administrador.this, TesteModelo.class);
                startActivity(i);
            }
        });


    }


    public static void gerarCSVData(BancoDados banco) {

        File root = Environment.getExternalStorageDirectory();
        File pasta = new File(root.getAbsolutePath());
        pasta.mkdirs();

        File arquivoXLS = new File(pasta, "caracteristicas.csv");
        FileOutputStream arquivosaida = null;

        try {
            arquivosaida = new FileOutputStream(arquivoXLS,true);

            arquivosaida.write(head.getBytes());

        } catch (Exception e) {
            System.out.println("Problemas ao inserir o cabecalho no file csv");
            e.printStackTrace();
        }

        for(Caracter dados : banco.getAllData()){

            String dadosItem = banco.getNameUser(dados.getIdUsuario())+
                    ","+dados.getIdSessao()+
                    ","+dados.getRepeticao()+
                    ","+dados.getCaracter()+
                    ","+dados.getDownTime()+
                    ","+dados.getUpTime()+"\n";
            System.out.println("\n***Dados: "+ dadosItem);
            try{
                arquivosaida.write(dadosItem.getBytes());

            } catch (IOException e){
                System.out.println("Problemas ao inserir dados no ficheiro csv");
                e.printStackTrace();

            }


        }

        // fechando o arquivo
        try {
            arquivosaida.flush();
            arquivosaida.close();

        }catch (IOException e){
            System.out.println("Falha ao fechar o arquivo");
           e.printStackTrace();

        }


    }



    // metodo para gerar csv dos usuarios

    public static void gerarcsvuser(BancoDados banco){

        File root = Environment.getExternalStorageDirectory();
        File pasta = new File(root.getAbsolutePath());
        pasta.mkdirs();

        File ficheiro = new File(pasta, "user.csv");


                FileOutputStream fi = null;
                try{
                    fi = new FileOutputStream(ficheiro,true);
                    byte [] bytH =Headeruser.getBytes();
                    fi.write(bytH);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    List<Usuario> listaU = banco.getAllUsers();
                    for(Usuario user: listaU){
                        String dados = user.getNome()+","+
                                user.getIdUsuario()+","+
                                user.getGenero()+","+
                                user.getFetaria()+","+"\n";
                        fi.write(dados.getBytes());
                    }

                    fi.flush();
                    fi.close();
                }catch (Exception e){
                    e.printStackTrace();
                }




    }

}
