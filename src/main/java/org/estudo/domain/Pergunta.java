package org.estudo.domain;

public class Pergunta {
    private String descricao;
    private boolean isEditavel;

    public Pergunta(String descricao) {
        this.descricao = descricao;
        this.isEditavel = true;
    }

    public Pergunta(String descricao, boolean isEditavel) {
        this.descricao = descricao;
        this.isEditavel = isEditavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean getIsEditavel() {
        return isEditavel;
    }

    public void setIsEditavel(boolean isEditavel) {
        this.isEditavel = isEditavel;
    }
}
