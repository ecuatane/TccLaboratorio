package com.example.tcc.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.tcc.R;
import com.example.tcc.dados.BancoDados;
import com.example.tcc.dados.Usuario;
import com.example.tcc.teclados.TecladoManager;
import com.example.tcc.teclados.fragmento;

public class Principal extends FragmentActivity {
    EditText editUser;
    BancoDados banco;
    String NameUser;
    private Vibrator vibrator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        fecharTeclado(this);
        editUser = (EditText) findViewById(R.id.username);
        editUser.requestFocus();
        banco = new BancoDados(getBaseContext());
        //banco.deleted();

        vibrator=  vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        addFragmentoNormal();
        Button butaoCadastrar = (Button) findViewById(R.id.buttonCadastrar);
        Button butaoEntrar = (Button) findViewById(R.id.buttonEntrar);


        butaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // System.out.println("\n\n*** Usuario:"+ editUser.getText().toString());
                Usuario user =banco.getUser(editUser.getText().toString());

                if (user != null) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

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
                            Principal.this);
                    builder.setTitle("Erro ao se registrar!");

                    builder.setMessage("Voce possui um registo, insira o seu username e click" +
                                    " no butao Entrar!")
                            .setPositiveButton("OK", dialogClickListener)
                            .show();

                }else{

                    Intent intent = new Intent(Principal.this, ActividadeRegistro.class);
                    intent.putExtra("name_u", editUser.getText().toString());
                    startActivity(intent);
                    editUser.setText("");

                }
            }


        });

        butaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUser.getText().toString().equals("eusto")){
                    Intent intent = new Intent(Principal.this, Administrador.class);

                    intent.putExtra("user_id", 1);
                    intent.putExtra("sessao_id", 1);
                    startActivity(intent);

                }else {
                    Usuario user =banco.getUser(editUser.getText().toString());

                     if(user!=null) {
                         System.out.println("***************id do usuario:\t"+user.getIdUsuario());
                         Intent intent = new Intent(Principal.this, ColectorDados.class);

                         intent.putExtra("user_id", user.getIdUsuario());
                         intent.putExtra("sessao_id", user.getIdSessao()+1);
                         intent.putExtra("nome", user.getNome());
                         // actualizar os dados da sessao do usuario
                         banco.updateUserSessionId(user.getIdUsuario(), user.getIdSessao()+1 );
                         startActivity(intent);
                     }else{
                         DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

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
                            Principal.this);
                    builder.setTitle("Erro ao entrar!");

                    builder.setMessage("Voce nao possui um registo, insira o seu username e click" +
                                    " no butao cadastrar!")
                            .setPositiveButton("OK", dialogClickListener)
                            .show();



                     }
                }

            }
        });

    }

    // metodo para fechar o teclado normal do SO android
public void fecharTeclado(Activity activity){
    View view = activity.getCurrentFocus();
    if(view!=null){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);}
}


// adiciona o fragmento do teclado normal
    public void addFragmentoNormal(){
        fragmento novoFragmento = new fragmento();
        TecladoManager tM  = new TecladoManager(getSupportFragmentManager(), novoFragmento,2,editUser);
        novoFragmento.setTecladoManager(tM);
        Auxiliar.addFragment(getSupportFragmentManager(), novoFragmento, R.id.fragment_normal);

    }
}
