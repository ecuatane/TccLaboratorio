package com.example.tcc.teclados;

import android.content.Context;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.tcc.R;
import com.example.tcc.dados.Caracter;
import com.example.tcc.dados.CaracterTest;
import com.example.tcc.dados.Usuario;



import java.util.ArrayList;

public class TecladoManager {
    private FragmentManager fragmentManager;
    private Fragment fragmentAtual;
    private fragmento tecladoFragment;

    private Button[] botoesTeclado;
    private EditText campoTextoAtual;
    private Caracter dadosCaractere;
    private CaracterTest dadosTeste;
    private Usuario dadosUsuario;
    private int idFragmentoAtual;
    private int repeticao = 1;
    private ArrayList<Caracter> vetorDadosCaractere;
    private ArrayList<CaracterTest> vetorDadosTeste;
    private boolean deletarUsado = false;
    private Vibrator vibrador;

    public TecladoManager(androidx.fragment.app.FragmentManager fm, Fragment f, int atividade, EditText et) {
        super();
        this.fragmentManager = fm;
        this.fragmentAtual = f;
        configurar(atividade, et);
    }

    public TecladoManager(androidx.fragment.app.FragmentManager fm, Context c, Fragment f, Usuario du, int atividade, EditText et) {
        super();
        this.fragmentManager = fm;
        this.fragmentAtual = f;
        this.dadosUsuario = du;
        configurar(atividade, et);
    }

    // Selecionando o fragmento a utilizar
    // fragmento normal, de colecta e de teste
    public void configurar(int atividade, EditText edit) {
        campoTextoAtual = edit;

        switch (atividade) {
            case 1:
                idFragmentoAtual = R.id.fragment_content_test;
                break;
            case 2:
                idFragmentoAtual = R.id.fragment_normal;
                break;
            case 3:
                idFragmentoAtual = R.id.fragment_colectar;
                break;
            default:
                break;
        }
    }

    public void setBotoes(Button[] teclas) {
        this.botoesTeclado = teclas;
    }

    public void escutarBotoes() {
        for (int i = 0; i < botoesTeclado.length; i++) {
            botoesTeclado[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (idFragmentoAtual == R.id.fragment_colectar) {
                        escreverSenha(view, motionEvent);
                    } else if (idFragmentoAtual == R.id.fragment_normal) {
                        escreverTexto(view, motionEvent);
                    } else if (idFragmentoAtual == R.id.fragment_content_test) {
                        escreverSenhaTeste(view, motionEvent);
                    }
                    return false;
                }
            });
        }
    }

    public boolean clicadoEspecial(String valor, EditText edit) {
        if (valor.equals(fragmentAtual.getResources().getString(R.string.delete))) {
            if (edit.getText().length() != 0) {
            }
            return true;
        } else if (valor.equals(fragmentAtual.getResources().getString(R.string.numbers))) {
            return true;
        } else if (valor.equals(fragmentAtual.getResources().getString(R.string.letters))) {
            return true;
        } else if (valor.equals(fragmentAtual.getResources().getString(R.string.Shift1))) {
            return true;
        }
        return false;
    }

    public boolean clicadoEspecial2(String valor, EditText edit) {
        if (valor.equals(fragmentAtual.getResources().getString(R.string.delete))) {
            if (edit.getText().length() != 0) {
            }
            return true;
        } else if (valor.equals(fragmentAtual.getResources().getString(R.string.numbers))) {
            return false;
        } else if (valor.equals(fragmentAtual.getResources().getString(R.string.letters))) {
            return false;
        } else if (valor.equals(fragmentAtual.getResources().getString(R.string.Shift1))) {
            return false;
        } else if (valor.equals(fragmentAtual.getResources().getString(R.string.Shift2))) {
        }
        return false;
    }

    public void escreverTexto(View v, MotionEvent motionEvent) {
        String texto = (String) ((Button) v).getText();
        vibrador.vibrate(38);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (texto.equals(fragmentAtual.getResources().getString(R.string.delete))) {
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            SystemClock.sleep(100);
                            campoTextoAtual.getText().clear();
                            return false;
                        }
                    });
                    v.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String texto = campoTextoAtual.getText() + "";
                            int posCursor = campoTextoAtual.getSelectionStart();
                            if (campoTextoAtual.getText().length() != 0 && posCursor != 0) {
                                texto = removerCaractere(texto, posCursor);
                                campoTextoAtual.setText(texto);
                                campoTextoAtual.setSelection(posCursor - 1);
                            }
                        }
                    });
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                int posCursor = campoTextoAtual.getSelectionStart();
                if (!clicadoEspecial(texto, campoTextoAtual)) {
                    if (texto.equals(fragmentAtual.getResources().getString(R.string.space))) {
                        campoTextoAtual.getText().insert(posCursor, " ");
                    } else {
                        campoTextoAtual.getText().insert(posCursor, texto);
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    public void escreverSenha(View v, MotionEvent ev) {
        String texto = (String) ((Button) v).getText();
        vibrador.vibrate(38);
        if (texto.equals(fragmentAtual.getResources().getString(R.string.go))) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                fragmentManager.beginTransaction().remove(fragmentAtual).commit();
                fragmentManager.popBackStack();
            }
            return;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (texto.equals(fragmentAtual.getResources().getString(R.string.delete))) {
                    deletarUsado = true;
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            SystemClock.sleep(100);
                            campoTextoAtual.getText().clear();
                            if (vetorDadosCaractere != null) {
                                vetorDadosCaractere.clear();
                            }
                            return false;
                        }
                    });
                    v.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String texto = campoTextoAtual.getText() + "";
                            int posCursor = campoTextoAtual.getSelectionStart();
                            if (campoTextoAtual.getText().length() != 0 && posCursor != 0) {
                                texto = removerCaractere(texto, posCursor);
                                campoTextoAtual.setText(texto);
                                campoTextoAtual.setSelection(posCursor - 1);
                            }
                        }
                    });
                }
                dadosCaractere = new Caracter();
                dadosCaractere.setDownTime(ev.getDownTime());
                break;
            }
            case MotionEvent.ACTION_UP: {
                int posCursor = campoTextoAtual.getSelectionStart();
                if (!clicadoEspecial(texto, campoTextoAtual)) {
                    if (texto.equals(fragmentAtual.getResources().getString(R.string.space))) {
                        campoTextoAtual.getText().insert(posCursor, " ");
                    } else {
                        campoTextoAtual.getText().insert(posCursor, texto);
                    }
                }
                dadosCaractere.setIdUsuario(dadosUsuario.getIdUsuario());
                dadosCaractere.setIdSessao(dadosUsuario.getIdSessao());
                dadosCaractere.setUpTime(ev.getEventTime());
                dadosCaractere.setCaracter(texto);
                if (vetorDadosCaractere != null) {
                    vetorDadosCaractere.add(dadosCaractere);
                }
                break;
            }
            default:
                break;
        }
    }

    public void escreverSenhaTeste(View v, MotionEvent ev) {
        String texto = (String) ((Button) v).getText();
        vibrador.vibrate(38);
        if (texto.equals(fragmentAtual.getResources().getString(R.string.go))) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                fragmentManager.beginTransaction().remove(fragmentAtual).commit();
                fragmentManager.popBackStack();
            }
            return;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (texto.equals(fragmentAtual.getResources().getString(R.string.delete))) {
                    deletarUsado = true;
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            SystemClock.sleep(100);
                            campoTextoAtual.getText().clear();
                            if (vetorDadosTeste != null) {
                                vetorDadosTeste.clear();
                            }
                            return false;
                        }
                    });
                    v.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String texto = campoTextoAtual.getText() + "";
                            int posCursor = campoTextoAtual.getSelectionStart();
                            if (campoTextoAtual.getText().length() != 0 && posCursor != 0) {
                                texto = removerCaractere(texto, posCursor);
                                campoTextoAtual.setText(texto);
                                campoTextoAtual.setSelection(posCursor - 1);
                            }
                        }
                    });
                }
                dadosTeste = new CaracterTest();
                dadosTeste.setDownTime(ev.getDownTime());
                break;
            }
            case MotionEvent.ACTION_UP: {
                int posCursor = campoTextoAtual.getSelectionStart();
                if (!clicadoEspecial(texto, campoTextoAtual)) {
                    if (texto.equals(fragmentAtual.getResources().getString(R.string.space))) {
                        campoTextoAtual.getText().insert(posCursor, " ");
                    } else {
                        campoTextoAtual.getText().insert(posCursor, texto);
                    }
                }
                dadosTeste.setUpTime(ev.getEventTime());
                dadosTeste.setCaracter(texto);
                if (vetorDadosTeste != null) {
                    vetorDadosTeste.add(dadosTeste);
                }
                break;
            }
            default:
                break;
        }
    }

    private static String removerCaractere(String str, int pos) {
        if (pos == str.length()) {
            return str.substring(0, pos - 1);
        } else {
            return str.substring(0, pos - 1) + str.substring(pos, str.length());
        }
    }

    public void setRepeticao(int repeticao) {
        this.repeticao = repeticao;
    }

    public void setVetorDadosCaractere(ArrayList<Caracter> vetor) {
        this.vetorDadosCaractere = vetor;
    }

    public void setVetorDadosTeste(ArrayList<CaracterTest> vetor) {
        this.vetorDadosTeste = vetor;
    }

    public void setDeletarUsado(boolean deletarUsado) {
        this.deletarUsado = deletarUsado;
    }

    public boolean foiDeletado() {
        return deletarUsado;
    }

    public void setVibrador(Vibrator vibrador) {
        this.vibrador = vibrador;
    }
}
