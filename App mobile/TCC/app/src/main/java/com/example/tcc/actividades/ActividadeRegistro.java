package com.example.tcc.actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.tcc.R;
import com.example.tcc.dados.BancoDados;
import com.example.tcc.dados.Usuario;

public class ActividadeRegistro extends FragmentActivity {
    private TextView TvNameUse;
    private RadioGroup radioSexo;
    private RadioGroup radioFaixa;
    Button btnguardar;
    private String add;
    private int idStart;
    Usuario novoUsuario = new Usuario();
    BancoDados bancoDados;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bancoDados = new BancoDados(getBaseContext());
        Intent intent =getIntent();
        String nome = intent.getExtras().getString("name_u");
        add=", " + nome+" complete o seu registo!";
        TvNameUse = (TextView) findViewById(R.id.tvLogIn);
        TvNameUse.setText(TvNameUse.getText() + add);
        idStart=bancoDados.getAllUsers().size();
        radioSexo = (RadioGroup) findViewById(R.id.radioGroupSex13);
        radioFaixa =(RadioGroup)  findViewById(R.id.faixaEtaria);
        btnguardar =(Button)  findViewById(R.id.buttonCadastrar);

        // ao clicar no butao guardar;

        btnguardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean erroSexo = false;
                boolean erroFaixa = false;

                int valorSexo = radioSexo.getCheckedRadioButtonId();

                int valorFaixa = radioFaixa.getCheckedRadioButtonId();

                RadioButton radioSexoSelected = (RadioButton) findViewById(valorSexo);


                if (valorSexo < 0) {
                    erroSexo = true;

                } else {

                    if (radioSexoSelected.getText().equals("Masculino")) {
                        novoUsuario.setGenero("Masculino");

                    } else {

                        novoUsuario.setGenero("Femenino");
                    }


                    RadioButton radioFaixa = (RadioButton) findViewById(valorFaixa);
                    if(valorFaixa<0) {
                        erroFaixa = true;
                    }else if(radioFaixa.getText().equals("15-25")){
                        novoUsuario.setFetaria("15-25");
                        erroFaixa=false;
                    }else if(radioFaixa.getText().equals("26-30")){
                        novoUsuario.setFetaria("26-30");
                        erroFaixa=false;

                    }else if(radioFaixa.getText().equals("31-50")){
                        novoUsuario.setFetaria("31-50");
                        erroFaixa=false;

                    }
                    novoUsuario.setIdUsuario(idStart+1);

                    novoUsuario.setIdSessao(1);
                    novoUsuario.setDadosColectados(3);
                    novoUsuario.setNome(nome);

                }


                // caso o usuario nao selecione o sexo ou a faixa etario
                DialogInterface.OnClickListener Ouvintedialogo = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case DialogInterface.BUTTON_POSITIVE:
                                dialog.cancel();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ActividadeRegistro.this);
                builder.setTitle("Erro ao se Registar");

                if (erroSexo) {

                    builder.setMessage("Selecione o seu sexo!").setPositiveButton("OK", Ouvintedialogo).show();
                }
                if (erroFaixa) {

                    builder.setMessage("Selecione a sua faixa etária !").setPositiveButton("OK", Ouvintedialogo).show();

                }
                DialogInterface.OnClickListener actionListener2 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {

                            case DialogInterface.BUTTON_POSITIVE:
                                bancoDados = new BancoDados(getBaseContext());
                                bancoDados.addUser(novoUsuario);
                                Intent intent = new Intent(ActividadeRegistro.this, ColectorDados.class);
                                intent.putExtra("user_id", novoUsuario.getIdUsuario());
                                intent.putExtra("sessao_id", novoUsuario.getIdSessao());
                                intent.putExtra("nome", novoUsuario.getNome());
                                startActivity(intent);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;

                        }
                    }
                };


                AlertDialog.Builder builder2 = new AlertDialog.Builder(
                        ActividadeRegistro.this);
                if (erroFaixa==false && erroSexo==false) {
                    builder2.setTitle("Termos e condições ");
                    builder2.setMessage("Permite que a App utilize teus dados somente para fins de estudo?")
                            .setPositiveButton("Aceito", actionListener2)
                            .setNegativeButton("Não", actionListener2).show();
                }
            }

        });
    }
}