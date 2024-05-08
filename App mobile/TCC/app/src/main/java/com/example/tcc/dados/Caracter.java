package com.example.tcc.dados;

public class Caracter {

    private long IdUsuario;
    private int idSessao;
    private String Caracter;
    private long DownTime;
    private long UpTime;
    private int Repeticao;


    public long getDownTime() {
        return DownTime;
    }

    public long getUpTime() {
        return UpTime;
    }

    public void setUpTime(long upTime) {
        UpTime = upTime;
    }

    public int getRepeticao() {
        return Repeticao;
    }

    public void setRepeticao(int repeticao) {
        Repeticao = repeticao;
    }

    public String getCaracter() {
        return Caracter;
    }

    public void setCaracter(String caracter) {
        Caracter = caracter;
    }

    public int getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(int idSessao) {
        this.idSessao = idSessao;
    }

    public long getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        IdUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return  IdUsuario + ", " + idSessao
                + ", " + Caracter
                + ", " + DownTime
                + ", " + UpTime
                + "\n";
    }

    public void setDownTime(long downTime) {
    }
}
