package com.example.tcc.actividades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.tcc.R;
import com.example.tcc.dados.BancoDados;
import com.example.tcc.dados.Caracter;
import com.example.tcc.dados.Usuario;
import com.example.tcc.teclados.TecladoManager;
import com.example.tcc.teclados.fragmento;

import java.util.ArrayList;

public class ColectorDados extends FragmentActivity {
    private ArrayList<Caracter> vector = new ArrayList<Caracter>();
    EditText editPassword;
    TextView tv,tvNome,tvID,tvContador;


    TecladoManager Tmanager;
    private String passValida="vida.nova", PassExacta;
    private long idUsuarioActual;
    private int idSessaoActual,repetion=0;
    private int amostra=1;
    private String nome;
    private BancoDados bancoDados;
    private ProgressDialog pogressoDialogo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        //editPassword = (EditText) findViewById(R.id.senha);
        closedKeyBoard(this);
        editPassword = (EditText) findViewById(R.id.senha);
        editPassword.requestFocus();
         tv = (TextView) findViewById(R.id.ver);
         tvNome= (TextView) findViewById(R.id.tvNome);
         tvID = (TextView) findViewById(R.id.tvID);
         tvContador =(TextView) findViewById(R.id.idCont);
        Bundle extras = getIntent().getExtras();
        idUsuarioActual = Long.valueOf(extras.getLong("user_id"));
        idSessaoActual = extras.getInt("sessao_id");
        nome= extras.getString("nome");
        tvNome.setText(nome);
        tvID.setText(String.valueOf(idSessaoActual));
        bancoDados = new BancoDados(getBaseContext());
       // bancoDados.deleted();

        Button butaoGuardar = (Button) findViewById(R.id.buttonCadastrar);

        // click do butao guardar
        butaoGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editPassword.getText().length()!=0){
                      if(editPassword.getText().toString().equals(passValida)){

                              if (!Tmanager.foiDeletado()) {
                                  if(verificarPass()) {
                                  if (amostra <= 50) {
                                      new AsyncTask<Void, Void, Void>() {
                                          @Override
                                          protected void onPreExecute() {
                                              super.onPreExecute();
                                              pogressoDialogo = ProgressDialog.show(ColectorDados.this, "Processando", "Aguarde um pouco...", true);
                                          }

                                          @Override
                                          protected Void doInBackground(Void... voids) {
                                              bancoDados.savePasswordKeyData(vector, amostra);

                                              vector.clear();

                                              return null;
                                          }

                                          @Override
                                          protected void onPostExecute(Void unused) {
                                              super.onPostExecute(unused);
                                              if (pogressoDialogo != null) {
                                                  pogressoDialogo.dismiss();
                                                  tvContador.setText(String.valueOf(amostra) + "-50");
                                                  amostra++;
                                              }
                                          }


                                      }.execute();
                                  } else {
                                      Toast.makeText(getApplicationContext(),
                                                      "Atingiu o numero maximo de amostras por sessao!", Toast.LENGTH_SHORT)
                                              .show();

                                  }


                              } else {
                                  vector.clear();
                                  Toast.makeText(getApplicationContext(),
                                                  "Dados nao colectados porque o butao shift ou 123? foi usado!", Toast.LENGTH_SHORT)
                                          .show();
                                      System.out.println("ENTROU 11111111");
                              }
                              // limpa o editText e reinicia o butao delete para o estado falso, para permitir a nova colecta
                              editPassword.setText("");
                                //Kfuncionalidade.setDeleteUsed(false);
                          }else{
                              vector.clear();
                              Toast.makeText(getApplicationContext(),
                                              "Dados nao colectados porque o butao  delete foi usado!", Toast.LENGTH_SHORT)
                                      .show();

                          }
                          editPassword.setText("");
                          Tmanager.setDeletarUsado(false);



                      }else{

                          Toast.makeText(getApplicationContext(),
                                          "Dados introduzidos sao incorrectos.", Toast.LENGTH_SHORT)
                                  .show();

                      }

                }else{

                    Toast.makeText(getApplicationContext(),
                                    "Por favor insira a palavra acima!", Toast.LENGTH_SHORT)
                            .show();



                }

            }
        });



        editPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
              // tv.setText(Long.valueOf(vier(1)).toString());
                ver();
                addFirstFragment();

                return true;
            }
        });

    }


// fechar o teclado padrao do android
    public void closedKeyBoard(Activity activity){
        View view = activity.getCurrentFocus();
        if(view!=null){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);}
    }

    // adicionar o fragmento com teclados
    public void addFirstFragment(){
        fragmento keypadContent = new fragmento();
        final boolean deleteUsado = Tmanager != null ? Tmanager.foiDeletado() : false;
        Tmanager = new TecladoManager(getSupportFragmentManager(), getBaseContext(), keypadContent,
                new Usuario(null,null,idUsuarioActual,idSessaoActual,0,null), 3, editPassword);
        Tmanager.setRepeticao(repetion);
        Tmanager.setVetorDadosCaractere(vector);
        Tmanager.setDeletarUsado(deleteUsado);
        keypadContent.setTecladoManager(Tmanager);
        Auxiliar.addFragment(getSupportFragmentManager(), keypadContent, R.id.fragment_colectar);

    }



    private void buildExactPassword() {
        boolean wasChar = true;
        PassExacta = "vida.nova";

    }

    /**
      verifica se os valores que estao no vector representam a pasword correcta

     */
    private boolean verificarPass() {
        String storedPass = "";
        for (Caracter c : vector) {
            storedPass += c.getCaracter();
        }
        if(storedPass.equals(passValida)){
            return true;
        }else{
        return false;}
    }


// tentando buscar itens do banco de dados

    public void  ver(){
       ArrayList<Caracter> lista = new ArrayList<>();
       ArrayList<Usuario> listaUsuario = new ArrayList<>();

        listaUsuario=bancoDados.getAllUsers();

        if(listaUsuario!=null){
            for(Usuario u : listaUsuario){
                System.out.println("\n\n ******Nome:\t " + u.getNome() + "\n");
                System.out.println("\n\n ******Faixa etaria:\t " + u.getFetaria() + "\n");
                System.out.println("\n\n ******Genero:\t " + u.getGenero()+ "\n");
                System.out.println("\n\n ******Dados_colectados:\t " + u.getDadosColectados() + "\n");
                System.out.println("\n\n ******UserId:\t " + u.getIdUsuario() + "\n");
                System.out.println("\n\n ******SessaoId:\t " + u.getIdSessao() + "\n");
                System.out.println("\n\n ******FimUsuario"+ "\n");
            }

        }else{
            System.out.println("\n\n ******\t: Nenhum Usuario! " );
        }





    }

}
