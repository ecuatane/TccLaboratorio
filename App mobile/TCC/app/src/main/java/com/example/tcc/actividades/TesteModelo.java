package com.example.tcc.actividades;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tcc.R;
import com.example.tcc.dados.BancoDados;
import com.example.tcc.dados.CaracterTest;
import com.example.tcc.dados.Caracteristicas;
import com.example.tcc.teclados.TecladoManager;
import com.example.tcc.teclados.fragmento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TesteModelo extends FragmentActivity {


    private EditText editTest;
    private  TextView txtResultado;
    private TextView txtPrecisao;
    private Button btnTest;
    TecladoManager Tmanager;
    String url ="http://projecto-tcc-302d471fcc89.herokuapp.com/predict";

    private String passValida="vida.nova", PassExacta;
    private ArrayList<CaracterTest> vector = new ArrayList<CaracterTest>();
    private Caracteristicas novasCaracteristicas;
    private int amostra=1;
    private BancoDados bancoDados;
    private ProgressDialog pogressoDialogo;
    private double [] array;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        editTest = (EditText)  findViewById(R.id.palavraTest);
        txtResultado = (TextView) findViewById(R.id.resultado);
        txtPrecisao = (TextView) findViewById(R.id.precisao);
        btnTest =(Button) findViewById(R.id.buttontestar);
        fragmentoTeclado();


        System.out.println("\n\n ******character:\t "  + "\n");


        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTest.getText().length()!=0){
                    if(editTest.getText().toString().equals(passValida)){

                       if (!Tmanager.foiDeletado()) {

                                    new AsyncTask<Void, Void, Void>() {
                                        @Override
                                        protected void onPreExecute() {
                                            super.onPreExecute();
                                            pogressoDialogo = ProgressDialog.show(TesteModelo.this, "Processando", "Aguarde um pouco...", true);
                                        }

                                        @Override
                                        protected Void doInBackground(Void... voids) {
                                            array = new Caracteristicas(vector).extrairFeatures();
                                            System.out.println("\n\n CARACTERISTICAS PARA O MODELO:"+ array.length);;
                                            vector.clear();
                                            return null;
                                        }

                                        @Override
                                        protected void onPostExecute(Void unused) {
                                            super.onPostExecute(unused);
                                            // aqui verificamos o resultado da classificacao

                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject jsonObject = new JSONObject(response);
                                                                String result= jsonObject.getString("placement");
                                                                if(!result.isEmpty()){
                                                                    txtPrecisao.setText(result);
                                                                }else{
                                                                   txtPrecisao.setText("Nenhum utilizador");
                                                                }


                                                            } catch (JSONException e) {
                                                                throw new RuntimeException(e);
                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {


                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(TesteModelo.this, "Erro de conexao"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                    System.out.println("\n\n\nERRO:"+error.getMessage().toString());
                                                }
                                            }){

                                                @Override
                                                protected Map<String,String> getParams(){
                                                    Map<String,String>  Params = new HashMap<String,String>();
                                                    Params.put("sessao", String.valueOf(1));
                                                    Params.put("repeticao", String.valueOf(1));
                                                    Params.put("Duracao.v", String.valueOf(array[0]));
                                                    Params.put("PP.v_i", String.valueOf(array[1]));
                                                    Params.put("SS.v_i", String.valueOf(array[2]));
                                                    Params.put("PS.v_i", String.valueOf(array[3]));
                                                    Params.put("Duracao.i", String.valueOf(array[4]));
                                                    Params.put("PP.i_d", String.valueOf(array[5]));
                                                    Params.put("SS.i_d", String.valueOf(array[6]));
                                                    Params.put("PS.i_d", String.valueOf(array[7]));
                                                    Params.put("Duracao.d", String.valueOf(array[8]));
                                                    Params.put("PP.d_a", String.valueOf(array[9]));
                                                    Params.put("SS.d_a", String.valueOf(array[10]));
                                                    Params.put("PS.d_a", String.valueOf(array[11]));
                                                    Params.put("Duracao.a", String.valueOf(array[12]));
                                                    Params.put("PP.a_ponto", String.valueOf(array[13]));
                                                    Params.put("SS.a_ponto", String.valueOf(array[14]));
                                                    Params.put("PS.a_ponto", String.valueOf(array[15]));
                                                    Params.put("Duracao.ponto", String.valueOf(array[16]));
                                                    Params.put("PP.ponto_n", String.valueOf(array[17]));
                                                    Params.put("SS.ponto_n", String.valueOf(array[18]));
                                                    Params.put("PS.ponto_n", String.valueOf(array[19]));
                                                    Params.put("Duracao.n", String.valueOf(array[20]));
                                                    Params.put("PP.n_o", String.valueOf(array[21]));
                                                    Params.put("SS.n_o", String.valueOf(array[22]));
                                                    Params.put("PS.n_o", String.valueOf(array[23]));
                                                    Params.put("Duracao.o", String.valueOf(array[24]));
                                                    Params.put("PP.o_v", String.valueOf(array[25]));
                                                    Params.put("SS.o_v", String.valueOf(array[26]));
                                                    Params.put("PS.o_v", String.valueOf(array[27]));
                                                    Params.put("Duracao.v2", String.valueOf(array[28]));
                                                    Params.put("PP.v_a", String.valueOf(array[29]));
                                                    Params.put("SS.v_a", String.valueOf(array[30]));
                                                    Params.put("PS.v_a", String.valueOf(array[31]));
                                                    Params.put("Media_Duracao", String.valueOf(array[32]));
                                                    Params.put("Media_PP", String.valueOf(array[33]));
                                                    Params.put("Media_PS", String.valueOf(array[34]));
                                                    Params.put("Media_SS", String.valueOf(array[35]));

                                                    return Params;
                                                }
                                            };

                                            RequestQueue queue = Volley.newRequestQueue( TesteModelo.this);
                                            queue.add(stringRequest);

                                            if (pogressoDialogo != null && pogressoDialogo.isShowing()) {
                                                pogressoDialogo.dismiss();
                                            }

                                        }



                                    }.execute();
                            editTest.setText("");

                           Tmanager.setDeletarUsado(false);
                        }else{
                            vector.clear();
                            Toast.makeText(getApplicationContext(),
                                            "Dados nao colectados porque o butao  delete foi usado!", Toast.LENGTH_SHORT)
                                    .show();

                        }
                        editTest.setText("");
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


        editTest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                fragmentoTeclado();

                return true;
            }
        });

    };

    private boolean verificarPass() {
        String storedPass = "";
        for (CaracterTest cd : vector) {
            storedPass += cd.getCaracter();
            System.out.println("\n\n ******character:\t " + cd.getCaracter() + "\n");
            System.out.println("\n\n ******DownTime:\t " + cd.getDownTime() + "\n");
            System.out.println("\n\n ******UpTime:\t " + cd.getUpTime());
            System.out.println("****fim222***\n");

        }
        if(storedPass.equals(passValida)){
            return true;
        }else{
            return false;}
    }

// adicionar fragmento para tela de test
    public void fragmentoTeclado(){
        fragmento novoFragmento = new fragmento();
        final boolean deleteUsado = Tmanager != null ? Tmanager.foiDeletado() : false;

        Tmanager  = new TecladoManager(getSupportFragmentManager(), novoFragmento,1,editTest);
        Tmanager.setVetorDadosTeste(vector);
        Tmanager.setDeletarUsado(deleteUsado);
        novoFragmento.setTecladoManager(Tmanager);
        Auxiliar.addFragment(getSupportFragmentManager(), novoFragmento, R.id.fragment_content_test);

    }


}
