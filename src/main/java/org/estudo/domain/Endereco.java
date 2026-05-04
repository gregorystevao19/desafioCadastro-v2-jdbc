package org.estudo.domain;

public class Endereco {
    private String cidade;
    private String rua;
    private int numeroCasa;

    public Endereco(String cidade, String rua, int numeroCasa) {
        this.cidade = cidade;
        this.rua = rua;
        this.numeroCasa = numeroCasa;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }
}
