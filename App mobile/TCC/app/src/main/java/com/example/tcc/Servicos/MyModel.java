package com.example.tcc.Servicos;

public class MyModel {
  String nome="";
 int sessao=0;

    public MyModel(String nome, int sessao) {
        this.nome = nome;
        this.sessao = sessao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSessao() {
        return sessao;
    }

    public void setSessao(int sessao) {
        this.sessao = sessao;
    }
}
