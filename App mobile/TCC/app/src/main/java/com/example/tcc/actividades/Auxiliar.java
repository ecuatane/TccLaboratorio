package com.example.tcc.actividades;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



public class Auxiliar {

    // Manipulador de fragmentos
    public static void addFragment(FragmentManager fm, Fragment fragment, int id) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.replace(id, fragment);

        }
        fragmentTransaction.commit();
    }



}
