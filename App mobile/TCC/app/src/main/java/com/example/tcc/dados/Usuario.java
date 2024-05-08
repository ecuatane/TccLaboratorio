package com.example.tcc.dados;

public class Usuario {

    private String nome;
    private String Fetaria;
    private long IdUsuario;
    private int IdSessao;
    private int dadosColectados;
    private String genero;

    private boolean selecao;



    public Usuario() {
    }

    public Usuario(String nome, String fetaria, long IdUsuario, int IdSessao, int dadosColectados, String genero) {
        this.nome = nome;
        this.Fetaria = fetaria;
        this.IdUsuario = IdUsuario;
        this.IdSessao = IdSessao;
        this.dadosColectados = dadosColectados;
        this.genero = genero;

    }

    public Usuario(String nome, int dadosColectados) {
        this.nome = nome;
        this.dadosColectados = dadosColectados;
    }

    public long getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        IdUsuario = idUsuario;
    }

    public int getIdSessao() {
        return IdSessao;
    }

    public void setIdSessao(int idSessao) {
        IdSessao = idSessao;
    }

    public int getDadosColectados() {
        return dadosColectados;
    }

    public void setDadosColectados(int dadosColectados) {
        this.dadosColectados = dadosColectados;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isSelecao() {
        return selecao;
    }

    public void setSelecao(boolean selecao) {
        this.selecao = selecao;
    }

    public String getFetaria() {
        return Fetaria;
    }

    public void setFetaria(String fetaria) {
        Fetaria = fetaria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", Fetaria='" + Fetaria + '\'' +
                ", IdUsuario=" + IdUsuario +
                ", IdSessao=" + IdSessao +
                ", dadosColectados=" + dadosColectados +
                ", genero='" + genero + '\'' +
                ", selecao=" + selecao +
                '}';
    }
}
