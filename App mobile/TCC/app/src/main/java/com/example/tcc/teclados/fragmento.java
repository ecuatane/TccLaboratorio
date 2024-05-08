package com.example.tcc.teclados;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tcc.R;


public class fragmento extends Fragment {
    private View view;
    private Button[] teclas = new Button[33];
    private TecladoManager tecladoManager;
    private Vibrator vibrator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.activity_main, container, false);
        vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
        initializeButtons();
        if (tecladoManager != null) {
            tecladoManager.setBotoes(teclas);
            tecladoManager.escutarBotoes();
            tecladoManager.setVibrador(vibrator);
        }
        return view;

    }

    private void initializeButtons() {
        teclas[0] = (Button) view.findViewById(R.id.buttonQ);
        teclas[1] = (Button) view.findViewById(R.id.buttonW);
        teclas[2] = (Button) view.findViewById(R.id.buttonE);
        teclas[3] = (Button) view.findViewById(R.id.buttonR);
        teclas[4] = (Button) view.findViewById(R.id.buttonT);
        teclas[5] = (Button) view.findViewById(R.id.buttonY);
        teclas[6] = (Button) view.findViewById(R.id.buttonU);

        teclas[7] = (Button) view.findViewById(R.id.buttonI);
        teclas[8] = (Button) view.findViewById(R.id.buttonO);
        teclas[9] = (Button) view.findViewById(R.id.buttonP);
        teclas[10] = (Button) view.findViewById(R.id.buttonA);
        teclas[11] = (Button) view.findViewById(R.id.buttonS);
        teclas[12] = (Button) view.findViewById(R.id.buttonD);
        teclas[13] = (Button) view.findViewById(R.id.buttonF);

        teclas[14] = (Button) view.findViewById(R.id.buttonG);
        teclas[15] = (Button) view.findViewById(R.id.buttonH);
        teclas[16] = (Button) view.findViewById(R.id.buttonJ);
        teclas[17] = (Button) view.findViewById(R.id.buttonK);
        teclas[18] = (Button) view.findViewById(R.id.buttonL);
        teclas[19] = (Button) view.findViewById(R.id.buttonShift);
        teclas[20] = (Button) view.findViewById(R.id.buttonZ);

        teclas[21] = (Button) view.findViewById(R.id.buttonX);
        teclas[22] = (Button) view.findViewById(R.id.buttonC);
        teclas[23] = (Button) view.findViewById(R.id.buttonV);
        teclas[24] = (Button) view.findViewById(R.id.buttonB);
        teclas[25] = (Button) view.findViewById(R.id.buttonN);
        teclas[26] = (Button) view.findViewById(R.id.buttonM);

        teclas[27] = (Button) view.findViewById(R.id.buttonOther);
        teclas[28] = (Button) view.findViewById(R.id.buttonSpace);
        teclas[29] = (Button) view.findViewById(R.id.buttonDot);
        teclas[30] = (Button) view.findViewById(R.id.buttonCommon);
        teclas[31] = (Button) view.findViewById(R.id.buttonDel);
        teclas[32] = (Button) view.findViewById(R.id.buttonGo);
    }

    public void setTecladoManager(TecladoManager tecladoManager) {
        this.tecladoManager = tecladoManager;
    }
}
